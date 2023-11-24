# 🏠 서울 시 지하철 노선 기반 거주지 추천 서비스

| 메인 서비스               |
|----------------------|
| ![img](gif/main.gif) |

**두 지하철 사이의 중심거리를 계산하여 가격에 알맞은 부동산 거래정보 제공**

| 사용자 관리               |
|----------------------|
| ![img](gif/user.gif) |

| 건의사항 관리               |
|-----------------------|
| ![img](gif/board.gif) |


---


# 핵심 로직 설명

## 지하철 노선 기반 중심거리 조회 서비스 - server

* 두개의 역 정보를 입력받아 해당 지점에서부터 각각 BFS 탐색하며 가중치를 추가한다
* 가중치를 저장하는 Map<String, Station>을 관리한다
* 탐색이 끝난 Map의 가중치를 합친다
* 합산된 가중치 결과를 확인하며 지하철 역정보에 따른 동 정보를 확인한다.

```java
public PriorityQueue<StationCost> findByStartAndEnd(String startStationName, String endStationName) {
        Station startStation = stationList.get(startStationName);
        Station endStation = stationList.get(endStationName);

        if (startStation == null || endStation == null) {
            log.debug("null 값이 존재함 에러 발생");
        }

        BFS(startStation, startStationValueMap);
        BFS(endStation, endStationValueMap);
        calcValueMap(startStationValueMap, endStationValueMap, priorityQueue);

        return priorityQueue;
    }
```

```java
 private void BFS(Station station, Map<String, Integer> stationValueMap) {
        Queue<StationCost> que = new ArrayDeque<>();
        que.offer(StationCost.builder().station(station).value(0).before(null).build());
        while (!que.isEmpty()) {
            StationCost cur = que.poll();
            if (stationValueMap.containsKey(cur.getStation().getName())) {
                continue;
            }
            stationValueMap.put(cur.getStation().getName(), cur.getValue());
            for (Station next : cur.getStation().getConnectStation()) {
                boolean isTransfer = true;
                if (cur.getBefore() != null) {
                    for (String curLine : cur.getBefore().getLine()) {
                        if (next.getLine().contains(curLine)) {
                            isTransfer = false;
                        }
                    }
                }
                if (isTransfer && cur.getBefore() != null) {
                    que.offer(StationCost.builder().station(next).value(cur.getValue() + 3).before(cur.getStation()).build());
                } else {
                    que.offer(StationCost.builder().station(next).value(cur.getValue() + 1).before(cur.getStation()).build());
                }

            }
        }
    }
```

* 결과
![valueResult.PNG](gif%2FvalueResult.PNG)


## 동 정보, 가격 토대로 필터링 서비스 - server

* 중간지점 지하철역을 확인한 후 동정보를 추출해서 Query를 실행한다.

```java
public List<HouseDto> findHouseByDong(String start,String end, String money, String page) {
        PriorityQueue<StationCost> priorityQueue = stationService.findByStartAndEnd(start, end);
        List<StationCost> stationCosts = priorityQueue.stream().toList();

        StationCost cost = stationCosts.get(Integer.parseInt(page));

        Set<String> dongSet = cost.getStation().getDong();
        String[] dongList = dongSet.toArray(String[]::new);

        /**
         * 지워야할 부분 - 이런식의 데이터가 들어감
         */
        String[][] dongListArray = new String[5][2];
        dongListArray[0] = new String[] {"신도림동","문래동"};
        dongListArray[1] = new String[] {"대림동"};
        dongListArray[2] = new String[] {"구로동"};
        dongListArray[3] = new String[] {"신길동"};
        dongListArray[4] = new String[] {"영등포동"};

        return mapper.findHouseByDong(dongListArray[Integer.parseInt(page)],money);
    }
```

* 아파트 정보와 아파트 거래내역 테이블을 조인한다
* where in 절을 사용하여 해당 동정보가 일치하는 아파트 내용 정보를 선택하고
* 사용자가 입력한 인자값 (money)의 범위 안에 들어가는 데이터로만 select 한다

```sql
    <select id="findHouseByDong" resultType="hashmap">
        select distinct h.house_id as houseId, h.build_year as buildYear , h.road_name as roadName, h.road_name_bonbun as roadNameBonbun,
        h.road_name_bubun as roadNameBubun,
        h.road_name_code as roadNameCode,
        h.road_name_sigungu_code as roadNameSigunguCode,
        h.dong,
        h.bubun,
        h.apartment_name as apartmentName,
        h.jibun
        from house h left join housedeal d on h.house_id = d.house_id
        where h.dong in
            <foreach collection="dongList" item="arr" open="(" close=")" separator=",">
                 #{arr}
            </foreach>
        and <![CDATA[CAST(replace(d.deal_amount,',','') as UNSIGNED) < #{money}]]>
    </select>

```


## kakao MAP 활용하기 - front

* input value 필터링 기능
* 저장된 역이름 정보들 리스트에서 입력한 내용이 포함되어 있다면 해당 `역이름 정보`를 반환
```js
computed: {
    filteredList() {
        if (this.searchTerm === "") {
            return this.dataList;
        }
        return this.dataList.filter((num) => {
            if (num.value.includes(this.searchTerm)) {
                return num;
            }
        });
    }
```

* axios-get 으로 거래 내역에 대한 정보를 가져와 v-if를 통해서 거래내역에 대한 컴포넌트들 생성

```js
 <VArticle v-if="isMounted" v-for:="item in articles.slice(0,10)" :house-deal="item" />

... axios

getHousedeal(
    params,
    ({ data }) => {
        articles.value = data;
        min.value =data.map(d => d.dealAmount).reduce((a,b) => a < b ? a:b);
        max.value =data.map(d => d.dealAmount).reduce((a,b) => a > b ? a:b);

    },
    (error) => {
        console.error(error);
    }
);
```

### 마커 화면에 표현하기
![marker.PNG](gif%2Fmarker.PNG)
* findMiddlePosition() -> watch(markName) -> setData() -> geocoder(주소전환) -> watch(markPosition) -> displayMarker()

#### 1. findMiddlePosition() : axios.get(params) : 집 정보들을 가져와 도로명 주소 생성
#### 2. watch(markName) : 감시자를 등록하여 markName 데이터가 변경될때 setData 메소드를 호출
#### 3. setData() : 도로명주소를 geocoder 를 통해서 Lat Lng 정보로 변경
#### 4. geocoder(주소전환) : Lat Lng 정보를 markPosition 저장
#### 5. watch(markPosition) : markPosition 정보가 변경될때 displayMarker() 호출 ( watch - deep (배열에 관한 감시))
#### 6. displayMarker() : 화면에 마커를 찍음    

## 

---

# 기술 스택
![skils.PNG](gif%2Fskils.PNG)

---

# ERD

![erd.png](/gif/erd.PNG)

---

