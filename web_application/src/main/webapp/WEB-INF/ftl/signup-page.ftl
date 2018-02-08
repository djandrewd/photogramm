<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="error" type="java.util.Optional<String>" -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Signup</title>
    <link href="/styles/signup-page.css" rel="stylesheet" type="text/css">
</head>
<body>
<form action="/signup/save" method="post">
    <div class="header">
        <h3>Create new photogramm user</h3>
    </div>
    <div class="container">
        <label><b>Nickname</b></label>
        <input type="text" placeholder="Enter nickname" name="nickname" required>

        <label><b>Email</b></label>
        <input type="text" placeholder="Enter email" name="email" required>

        <label><b>Name</b></label>
        <input type="text" placeholder="Enter name" name="name" required>

        <label><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="password" required>

        <label><b>Repeat Password</b></label>
        <input type="password" placeholder="Repeat Password" name="psw-repeat" required>
        <input type="checkbox" checked="checked"> Remember me
        <p>By creating an account you agree to our <a href="#">Terms & Privacy</a>.</p>

        <div class="clearfix">
            <button type="submit" class="signupbtn">Sign Up</button>
        </div>

        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </div>
</form>
</body>
</html>