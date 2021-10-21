<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>

    <link rel="stylesheet" rel="preload" as="style" onload="this.rel='stylesheet';this.onload=null" href="https://fonts.googleapis.com/css?family=Roboto:300,300italic,700,700italic">
    <link rel="stylesheet" rel="preload" as="style" onload="this.rel='stylesheet';this.onload=null" href="https://unpkg.com/normalize.css@8.0.0/normalize.css">
    <link rel="stylesheet" rel="preload" as="style" onload="this.rel='stylesheet';this.onload=null" href="https://unpkg.com/milligram@1.3.0/dist/milligram.min.css">
    <script src="js/jsQR.js"></script>

</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>

<div>
    <a class="button" id="decodeButton">Decode</a>
</div>

<div>
    <input type='file' />
    <br><img id="img" src="#">
</div>

<label>Result:</label>
<pre><code id="result"></code></pre>

<canvas id="canvas"></canvas>


<form action="hello-servlet" method="get" id="formDgc">
    <input type="text" name="dgc" id="dgc" >
    <input type="submit">
</form>
<a href="hello-servlet">Hello Servlet</a>


<script type="text/javascript">

    function getArrayFromURI(){

        return imageData;
    }

    window.addEventListener('load', function () {
        document.querySelector('input[type="file"]').addEventListener('change', function() {
            if (this.files && this.files[0]) {
                var img = document.querySelector('img');


                img.src = URL.createObjectURL(this.files[0]); // set src to blob url
                const canvas = document.querySelector("canvas");
            }
        });

        document.getElementById('decodeButton').addEventListener('click', () => {
            var canvasElement = document.getElementById("canvas");
            var canvas = canvasElement.getContext("2d");

            var img = document.querySelector('img');
            var imageData;

                console.log("sono nell'onload");
                canvasElement.height = img.height;
                canvasElement.width = img.width;
                canvas.drawImage(img, 0, 0, canvasElement.width, canvasElement.height);
                imageData = canvas.getImageData(0, 0, canvasElement.width, canvasElement.height);


            const code = jsQR(imageData.data, imageData.width, imageData.height,  {
                inversionAttempts: "dontInvert",
            });
            if (code) {
                console.log("Found QR code", code.data);
                let form = document.getElementById("formDgc");
                let inputDgc = document.getElementById("dgc");
                inputDgc.setAttribute("value", code.data);
                form.submit();
            } else {
                console.log("Code not found");
            }
            console.log(`Started decode for image from ${img.src}`)
            URL.revokeObjectURL(img.src);
        })

    })
</script>
<script>

</script>
</body>

</html>