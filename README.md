# Side-Project-Urkunde

## Contents

- [Introduction](#Introduction)
- [Requriements](#Requriements)
- [Installation](#Installation)
- [Contributing](#Contributing)
- [Maintainer](#Maintainer)

## Introduction

- REST API Server
- FrontEnd , BackEnd를 나눠서 각자 개발
    - BackEnd 담당
- 시험 전이나, 개인적인 공부를 끝낸 후 가벼운 점검을 위한 웹 앱

- 문제 상황
    - 종이로 계속 점검하기에는 종이 사용량이 증가함
    - 손으로 계속 쓰다보면 손이 아플 수 있음
- 프로젝트 목표
    - 누구나 사용할 수 있는 쉬운 사용법과 함께 공부한 것에 대한 점검을 할 수 있는 웹 앱을 만들자

- **퀴즈 CRUD**
    
<div align=center>

<img width="600" alt="image" src="https://user-images.githubusercontent.com/91618389/186843381-8270cf6e-6e9f-4f6b-b992-6428985101c6.png">

</div>
  
    

- **퀴즈 점검**
    
<div align=center>

<img width="600" alt="image" src="https://user-images.githubusercontent.com/91618389/186843378-df9b977b-60e0-41da-bc33-053efebd4870.png">

</div>
    

- **퀴즈 푼 상태 기록**
    
<div align=center>

<img width="600" alt="image" src="https://user-images.githubusercontent.com/91618389/186843369-4d663bc5-2982-4a30-9fee-d47d422003f0.png">

</div>
    

**<기능>**

- 퀴즈 문제 CRUD
- 퀴즈 점검(문제 풀기)
- 주간별로 퀴즈 푼 상태를  색깔별로 기록 가능
    - 해당 날짜에 문제를 다 풀었을 경우 : **True (초록색)**
    - 다 맞추지도 못한 경우 : **False (주황색)**
    - 아무런 문제도 풀지 않은 경우 : **None (없음)**


**<사용 기술>**

- Spring Boot
- Spring Data JPA
- MariaDB
- Oracle Server


## Requirements



- JDK 11 or later
- Gradle 4+
- You can also import the code straight into your IDE:
    - [Spring Tool Suite (STS)](https://spring.io/tools)
    - [IntelliJ IDEA](https://www.jetbrains.com/)

## ****Installation****



- Install as you would normally install a Java JDK 11
- If you use IntelliJ, you must install lombok plugin
    - Click *File > Settings > Plugin* or *Ctrl+Alt+S > Plugin*
    - Search **lombok** and Install
    

## Contributing



1. Create issues about the work.
2. Create a branch on the issue.
3. Commit, push to the created branch.
4. When the work is completed, request a pull request to main branch after rebaseing the main branch.
5. Review the code and merge it

### Branching

`ISSUE_NUMBER-description`

- e.g. Issue 5 related to  upload img
    
    `2-img-upload`
    

### Commit Message

Referred to [Beom Dev Log](https://beomseok95.tistory.com/328) and [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/)

```
<type>[optional scope]: <description>
[optional body]
[optional footer(s)]
```

- Type
    - build, docs, feat, fix, perf, reactor, test
- Example
    
    `feat: allow provided config object to extend other configs`
    

## Maintainer



Current maintainers:

- SooChan Lee - [https://github.com/soochangoforit](https://github.com/soochangoforit)
