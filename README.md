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
model.addAttribute("startNum", startNum);를 사용해 글 시작 번호도 board_list에 전달해준다.</br>

2024 11 20 - 9주차 로그인과 로그아웃 -1 완료
-----------------------------------------------------
ppt의 소스 코드를 복사했지만 </br>
Action:</br>
</br>
Consider defining a bean of type 'org.springframework.security.crypto.password.PasswordEncoder' in your configuration. 이 떴다.</br>
PassworeEncoder라는 Bean을 주입시킬 수 없다는데, @Configuration을 SecurityConfig.java에서 빠뜨린 것이 원인이였다.</br>
@Bean(명시적 의존성 주입)은 서버 시작 전에 객체를 주입시켜준다. 서버 구동 중간에 객체를 주입시키는 @Autowired와 객체 주입 시점이 다른 것이다.</br>
이것 외엔 별다른 에러 없이 로그인 및 로그아웃 기능 구현에 성공했다.</br>

2024 11 20 - 9주차 연습문제(변수 필드 검증 추가)완료
-----------------------------------------------------
DTO 파일(AddMemberRequest.java)에 변수 검증방식을 추가했고, 폼을 입력하는 즉시 검증에 오류가 있으면 화면에 출력할 수 있게 join_new에 오류 메시지를 출력하는 부분을 추가하고</br>
MemberController.java의 addMembers에 Model을 추가하여 model.addAttribute("errors", bindingResult.getAllErrors());를 입력해 오류가 있으면 메시지를 출력하게 했다.
또한 힌트대로 MemberController.java의 checkMembers, addmembers와 MemberService.java의 validateDuplicateMember, saveMember, loginCheck와 같은 검증이 필요해 보이는 메서드의 @Vaild를 추가했고,</br>
service 폴더 내에 있는 파일은 @Vaildated를 추가했다.</br>
그러고 나서 하나하나 직접 검증해보면서 검증 자체는 잘 되는 것을 확인했으나, 혹시 몰라서 정상적으로 입력했을 때를 테스트 해봤는데, 이상하게도 되지 않았다. gpt로 만든 이름(name)검증 방식에 한글이 포함되지 않아서였다.</br>
매우 허무했으나 아무튼 한글도 설정하면서 모든 것이 정상적인 것을 확인했다.</br>


2024 12 03 - 10주차 로그인과 로그아웃 -2 및 연습문제 '보안 설정 찾아보기' 완료
-----------------------------------------------------
</br>
BlogController의 board_list를 GET 방식으로 맵핑하는 부분에 세션에서 현재 로그인한 사용자의 이메일을 전달하는 확인하는 부분을 만들고 게시판에 전달할 수 있도록 만들었다.</br>
