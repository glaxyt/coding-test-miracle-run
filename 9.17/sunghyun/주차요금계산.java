import java.util.*;

class Solution {
    static String IN = "IN";
    static String OUT = "OUT";
    static int UNTIL_NIGHT = 23 * 60 + 59;
    // 들어온 차 번호만 입력할 예정, 시간 -> 분으로 전환해서 계산
    static Map<String, Integer> timeInLine = new HashMap<>();
    static Map<String, Integer> timeManager = new HashMap<>();
    static Map<String, Integer> feeManager = new HashMap<>();


    public int[] solution(int[] fees, String[] records) {
        int[] answer = {};
        // 차량 입차 된 후 출차 내역 없음.

        // fee = ["기본 시간", "기본 요금", "단위 시간", "단위 요금"]
        int standardTime = fees[0];
        int standardFee = fees[1];
        int perMinute = fees[2];
        int perMinuteFee = fees[3];

        // 기본 시간보다 크다면, 단위 시간(분)마다 요금 측정하여 더하기
        for (String record : records) {
            String[] recordSplit = record.split(" ");
            // ["05:34", "5961", "IN"]
            // 자료구조는 해시 맵 선정 이유: 큐, 스택, 우선순위 큐 어울리지않음
            String[] times = recordSplit[0].split(":");
            String carNum = recordSplit[1];
            String carStatus = recordSplit[2];

            int hour = Integer.parseInt(times[0]);
            int minute = Integer.parseInt(times[1]);

            int totalMinutes = hour * 60 + minute;

            if (carStatus.equals(IN)) {
                timeInLine.put(carNum, totalMinutes);
            } else {
                int startTime = timeInLine.get(carNum);
                int totalFee = standardFee;
                int useTime = totalMinutes - startTime;
                timeManager.put(carNum, timeManager.getOrDefault(carNum, 0) + useTime);
                timeInLine.remove(carNum);
            }
        }

        for (String carNum : timeInLine.keySet()) {
            int leftOverTime = timeInLine.get(carNum);
            int useTime = UNTIL_NIGHT - leftOverTime;
            timeManager.put(carNum, timeManager.getOrDefault(carNum, 0) + useTime);
        }

        for (String carNum : timeManager.keySet()) {
            int parkingTime = timeManager.get(carNum);
            int totalUseTime = parkingTime - standardTime;
            int totalFee = standardFee;

            if (totalUseTime > 0) {
                totalFee += (int) (Math.ceil((double) totalUseTime / perMinute)) * perMinuteFee;
            }
            feeManager.put(carNum, totalFee);
        }

        List<String> carNums = new ArrayList<>(feeManager.keySet());

        Collections.sort(carNums);

        answer = new int[carNums.size()];
        for (int i = 0; i < carNums.size(); i++) {
            answer[i] = feeManager.get(carNums.get(i));
        }

        return answer;
    }
}