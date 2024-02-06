function movieHide() {
  // 해당 div 요소 가져오기
  var movieForm = document.getElementById("movieHide");

  // 현재 상태 확인 후 토글
  if (movieForm.style.display === "none") {
    // 보이지 않는 상태일 때, 보이도록 변경
    movieForm.style.display = "block";
  } else {
    // 보이는 상태일 때, 숨기도록 변경
    movieForm.style.display = "none";
  }
}
