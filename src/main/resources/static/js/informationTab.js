function informationTab(tabName) {
    // 모든 탭과 탭 내용 숨기기
    var tabs = document.getElementsByClassName('tab-content');
    for (var i = 0; i < tabs.length; i++) {
        tabs[i].style.display = 'none';
    }

    // 모든 탭의 활성화 스타일 제거
    var tabButtons = document.getElementsByClassName('tab');
    for (var i = 0; i < tabButtons.length; i++) {
        tabButtons[i].classList.remove('active-tab');
    }

    // 선택된 탭과 해당 탭 내용 표시
    document.getElementById(tabName).style.display = 'block';
    event.currentTarget.classList.add('active-tab');
}