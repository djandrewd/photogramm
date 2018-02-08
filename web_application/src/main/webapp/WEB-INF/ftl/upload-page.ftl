<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="error" type="java.util.Optional<String>" -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Upload an image</title>
    <link rel="stylesheet" href="/styles/upload-page.css" type="text/css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
<div id="upload-container">
    <form method="post" action="upload/img" enctype="multipart/form-data">
        <div id="img-name-lbl">
            <label><b>Image name</b></label>
        </div>
        <div id="img-name-val">
            <textarea id="upload-text" name="name" placeholder="Image description"></textarea>
        </div>
        <div id="img-upload-lbl">
            <label><b>Upload image</b></label>
        </div>
        <div id="img-upload-file">
            <input type="file" accept="image/*" name="file" onchange="loadFile(event)" class="btn btn-default">
        </div>
        <div id="img-submit-btn" >
            <input type="submit" value="Upload file" class="btn btn-default">
        </div>
        <div id="img-upload-value">
            <img id="img-preview"/>
        </div>

        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</div>
<script type="text/javascript"
        src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script>
    let loadFile = function (event) {
        let output = $('#img-preview');
        output.attr('width', 160)
                .attr('height', 120)
                .attr('src', URL.createObjectURL(event.target.files[0]));
    };
</script>
</body>
</html>