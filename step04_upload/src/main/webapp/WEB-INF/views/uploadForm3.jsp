<%@ page contentType="text/html;charset=UTF-8" language="java"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>spring boot UPload3 </h1>
<form  action="/upload3"  enctype="multipart/form-data" method="post">
    제목 <input type="text" name="subject" /></p>
    <input type="file" name="uploadFile" />
    <input type="submit" value="업로드"/>
</form>

<c:if test="${not empty msg}" > ${msg}</c:if>
<script>
    window.addEventListener("DOMContentLoaded", function () {
        const form = document.querySelector("form");
        const fileInput = form.querySelector("input[type='file']");
        const maxSize = 20 * 1024 * 1024; // 20MB

        form.addEventListener("submit", function (e) {
            const file = fileInput.files[0];

            if (!file) return; // 파일이 없는 경우는 그냥 통과

            if (file.size > maxSize) {
                alert("20MB 이하 파일만 업로드 가능합니다.");
                e.preventDefault(); // 폼 전송 중지
            }
        });
    });
</script>
</body>
</html>