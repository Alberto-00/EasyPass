/***********************
 * SCORRIMENTO SIDENAV *
 ***********************/
$(document).ready(function (){
    $("img.angle-double-up").click(function (){
        const col1 = document.getElementsByClassName("coll-1")[0];
        const col2 = document.getElementsByClassName("coll-2")[0];
        col1.classList.toggle("collapsee");
        col2.classList.toggle("full--width");
    });
})


/**********
 * LOGOUT *
 **********/
// Modal
const modal = document.getElementById("myModal");
// Apertura Modal
const logout = document.getElementById("logout");
// Span che chiude modal
const span = document.getElementsByClassName("close")[0];

// funzione per aprire modal
logout.onclick = function() {
    modal.style.display = "block";
}

// Funzione per chiudere modal (Dalla "X")
span.onclick = function() {
    modal.style.display = "none";
}

//Funzione per chiudere modal (Dal bottone "No")
document.getElementById("close").onclick = function (){
    modal.style.display = "none";
}

// Funzione per chiudere modal quando l'utente clicca al di fuori
window.onclick = function(event) {
    if (event.target === modal) {
        modal.style.display = "none";
    }
}


/*************
 * Scroll Up *
 *************/
window.addEventListener("scroll", scrollFunction);
const scrollUp = document.getElementById("scrollUp");
window.onscroll = function() {scrollFunction()};
function scrollFunction() {
    if (document.body.scrollTop > 250 || document.documentElement.scrollTop > 250)
        scrollUp.style.display = "block";
    else
        scrollUp.style.display = "none";
}