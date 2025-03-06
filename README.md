# TimerList 설명

## 메인화면
RecyclerView 활용 ViewHolder 2개로 분할

- 시간은 3초로 통일
- RecyclerView 1개
- ViewHolder 2개(A~Z 랜덤 50), 휴지통 아이콘 포함(리스트에 휴지통 아이콘 + String)
- 목록
    - 목록은 50개 랜덤.
    - 아이템을 누르면 휴지통으로 이동
- 휴지통
    - 휴지통에서 복구를 하면 목록으로 복구
    - 휴지통은 n초 후에 자동으로 삭제
        - 자동 삭제 제약 조건
            - 만약 A 어댑터로 바로 이동하면, 자동삭제는 진행하지 않음.
            - 아이템을 복구하면 다시 n초 카운트

## 완성 샘플 이미지
<p align="center">
  <img src="https://github.com/ejkim-dev/TimerList/blob/main/sample/screenshot_20250225_172057.png" width="25%">
  <img src="https://github.com/ejkim-dev/TimerList/blob/main/sample/screenshot_20250225_171938.png" width="25%">
    <img src="https://github.com/ejkim-dev/TimerList/blob/main/sample/timer_list_record.gif" width="25%">
</p>

## 기술 스택
- **언어:** Kotlin  
- **UI:** XML + ViewBinding
- **비동기 처리:** LiveData, CountDownTimer
- **상태 관리:** ViewModel

## 개발 환경
- **Android Studio:**  Ladybug Feature Drop | 2024.2.2 
- **Gradle 버전:** 8.9
- **Min SDK:** 26  
- **Target SDK:** 35

  
