$(document).ready(function () {
    const actualBtn = document.getElementById('actual-btn');
    const fileChosen = document.getElementById('file-chosen');

    actualBtn.addEventListener('change', function(){
        fileChosen.textContent = this.files[0].name
    })
})


$(function(){
    $("#myButton").click(function () {
        const str = document.getElementById('file-chosen').innerText;
        if(str.endsWith(".jpg") || str.endsWith(".jpeg") || str.endsWith(".png") || str.endsWith(".svg") || str.endsWith(".pdf")) {
            document.getElementById("myButton").setAttribute('type', 'submit');
        }
        else {
            bootbox.alert("Errore! Formato non valido");
        }
    })
})
