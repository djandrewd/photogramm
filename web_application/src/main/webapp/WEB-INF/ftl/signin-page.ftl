<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="error" type="java.util.Optional<String>" -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sign-in</title>
    <link href="/styles/signin-page.css" rel="stylesheet" type="text/css">
</head>
<body>
 <form action="/signin" method="post">
    <div class="container">
        <label><b>Username</b></label>
        <input type="text" placeholder="Enter Username" name="username" required>

        <label><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="password" required>

        <button type="submit">Login</button>
        <br/>
        <input type="checkbox" checked="checked" name="remember-me"/> Remember me
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </div>

    <div class="container">
        Create <a href="/signup">account?</a>
    </div>
</form>
</body>
</html>