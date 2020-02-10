<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
@font-face { font-family: 'TmonMonsori'; src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_two@1.0/TmonMonsori.woff') format('woff'); font-weight: normal; font-style: normal; }
</style>

<nav class="heun-header-nav navbar navbar-expand-lg navbar-dark navbar-over absolute-top" id="menu">
  <div class="container">
  
    <div class="heun-title form-inline">
         <a class="heun-logo" style="margin-bottom:10px" href="/index">
         <img src="/mvc/resources/img/heunheun1.png" width="60" height="50"></a>
         
     <a href=""><h4 style="font-family: 'TmonMonsori'; font-size: 35px; color: #3b8ced">흔흔트립</h4></a>
    </div>
    <div class="collapse navbar-collapse d-flex flex-row-reverse" id="menu-content">

      <ul class="navbar-nav p-2">
        <li class="nav-item dropdown user-account">
          <a id='login-username' class="nav-link dropdown-toggle" href="#" role="button" data-toggle="dropdown"
            aria-haspopup="true" aria-expanded="false">
            <span id='login-userphoto' class="user-image"></span> 안녕하세요
          </a>
          <div class="dropdown-menu">
            <a id='login-btn' href="auth/signin" class="dropdown-item">로그인</a>
            <a id='register-btn' href="auth/register" class="dropdown-item">회원가입</a> 
            <a id='logout-btn' href="#" class="dropdown-item">로그아웃</a> 
            <a id='mypage-btn' href="#" class="dropdown-item heun-myPage">마이페이지</a> 
            <a id='message-btn' href="/heunheuntrip/html/message/index.html" class="dropdown-item heun-message">메세지</a> 
          </div>
        </li>
      </ul>
      
     <form id="heun-header-search" class="heun-search-form p-2">
      <fieldset>
        <input id="heun-search-val" class="heun-hsearch" type="search" />
        <button class="heun-hsearch-btn" type="submit">
        <i class="fa fa-search"></i>
        </button>
      </fieldset>
    </form>
    </div>
  </div>
</nav>
<script src='/mvc/resources/js/header.js'></script>
