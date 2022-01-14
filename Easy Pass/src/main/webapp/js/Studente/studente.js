$(document).ready(function () {
    const actualBtn = document.getElementById('actual-btn');

    actualBtn.addEventListener('change', function(){
        $('#file-chosen').remove();
        actualBtn.insertAdjacentHTML('afterend', "<span id='file-chosen'>" + this.files[0].name + "</span>")
    })

    $("#myButton").click(function () {
        let str = document.getElementById('file-chosen')
            if(str != null){
                str = str.innerText;
                if(str.endsWith(".jpg") || str.endsWith(".jpeg") || str.endsWith(".png") ||
                    str.endsWith(".JPG") || str.endsWith(".JPEG") || str.endsWith(".PNG")) {
                    document.getElementById("myButton").setAttribute('type', 'submit');
                }
                else
                    bootbox.alert("Errore! Formato non valido.");
            }
    })
})