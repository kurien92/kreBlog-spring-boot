# kreBlog-spring-boot
**스프링 레거시 프로젝트를 스프링 부트로 전환하는 작업을 진행하는 프로젝트입니다.**<br><br>
블로그 : https://www.kurien.net
<br>
<br>
기존에 https://github.com/kurien92/kreBlog 에서 개발하던 Legacy Spring을 Spring Boot로 Migration하고 이후 다음과 같은 부분을 개선할 예정입니다.
<br>
<br>
**작업 예정 사항**
* MyBatis를 JPA로 변경
  * MyBatis 사용 시 쿼리 작성 및 Dao 제작으로 인한 생산성 저하
  * JPA 사용으로 생산성 향상 기대
* JSP를 thymeleaf로 변경
  * JSP 사용 시 jar로 빌드하여 사용 어려움
  * thymeleaf 사용 시 확장자가 html이므로 프론트 개발에 용이
* CI/CD 수정
  * 빌드 환경과 구동 환경, git Repo 변경에 따라 기존 CI/CD 설정이 불가하므로 수정 필요
* profile을 local, dev, prod 환경에 따라 분기 처리
    
<br>
<br>
  
**작업 완료 사항**
* Legacy Spring을 Spring Boot로 변경
  * Spring Boot 사용으로 Legacy Spring 관련 최신자료를 점차 찾기 어려워짐. 
  * 설정이 상대적으로 편하여 
* maven을 gradle로 변경
* xml context들을 Java Config로 변경
  * xml 방식은 오류 발생 시 오류 원인을 찾기 어려움.

##PC화면
![스크린샷 2021-03-06 오전 2 22 36](https://user-images.githubusercontent.com/9585009/110150875-1b2fba80-7e23-11eb-9645-d9940c7e6e53.png)

## 모바일 화면
![스크린샷 2021-03-06 오전 2 23 11](https://user-images.githubusercontent.com/9585009/110150887-1ff46e80-7e23-11eb-936e-0bb258324280.png)
