2024 10 19 - 1~5주차 연습문제 모두 완료<br/>
-----------------------------------------------------

2024 10 23 - 게시판 삭제버튼 오류조치 완료<br/>
-----------------------------------------------------
@DeleteMapping 부분이 BlogRestController 안에 있었음 안에 있어서도 안됐고 애초에 BlogController 안에 왜 BlogRestController가 있었는지 불명
BlogRestController 삭제 후 DeleteMapping 올바른 위치에 입력 후 정상 작동 확인

2024 11 06 - 7주차 연습문제 일부 완료<br/>
-----------------------------------------------------
게시판 글 확인창에 수정, 삭제 버튼 동작 확인(전에 사용했던 코드 사용)

2024 11 13 - 7주차 연습문제 모두 완료, 8주차 글쓰기 기능까지 완료<br/>
-----------------------------------------------------
글쓰기 기능은 문제없이 작동됐으나 title, content 변수만 입력됨 -> BlogService.java의 article.update()메서드의 파라미터가 두 개밖에 없었다.←7주차 추가문제였음</br>
이후 추가하여 글쓰기 기능까지 이상없음

2024 11 13 - 8주차 게시판 검색기능, 연습문제 완료<br/>
-----------------------------------------------------
게시판의 검색창과 페이징 구현 때 Pageable cannot be resolved to a typeJava(16777218) 또는 The type Page is not generic; it cannot be parameterized with arguments <Board> 와 같은 에러 메시지가 떴었다.</br>
해결 방법은 Page, Pageable클래스를 임포트 해줬다.</br>
import org.springframework.data.domain.Page;</br>
import org.springframework.data.domain.Pageable;</br>

연습문제는 BlogController.java에서 startNum(글 시작 번호)을 계산하고, 이를 board_list.html로 전달하는 방식이였다</br>
기존의 매핑에서 PageRequest pageable = PageRequest.of(page, 3); 을</br>

int pageSize = 3;</br>
PageRequest pageable = PageRequest.of(page, pageSize);</br>로 수정하여 게시글 개수를 int형으로 선언했다.</br>이후 int startNum = (page * pageSize) + 1;도 사용해 startNum도 선언해주고,</br>
model.addAttribute("startNum", startNum);를 사용해 글 시작 번호도 board_list에 전달해준다.
