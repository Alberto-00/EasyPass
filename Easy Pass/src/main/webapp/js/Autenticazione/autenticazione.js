$(document).ready(function (){
    $('#sign-in').click(function (){
        $('#login-in').addClass('none')
        $('#login-up').removeClass("none")
    });

    $('#sign-up').click(function (){
        $('#login-up').addClass('none')
        $('#login-in').removeClass('none')
    });

    $("form[name='admin-login']").validate({
        rules: {
            email: {
                required: true,
                email: true
            },
            password: {
                required: true,
            }
        },
        messages: {
            password: {
                required: "Inserire la password.",
            },
            email: {
                required: "Inserire l'e-mail.",
                email: "Inserire un'e-mail valida."
            }
        },
        submitHandler: function(form) {
            form.submit();
        }
    });

    $("form[name='admin-registry']").validate({
        rules: {
            nome: {
                required: true,
                name: true,
            },
            cognome: {
                required: true,
                name: true,
            },
            email2: {
                required: true,
                email: true
            },
            password2: {
                required: true,
                checklower: true,
                checkupper: true,
                checkdigit: true,
                checkspecial: true,
                minlength: 8,
            }
        },
        messages: {
            password2: {
                required: "Inserire la password.",
                strong_password: "La password non è sicura.",
                minlength: "La password deve essere almeno di 8 caratteri.",
                checklower: "La password deve contenere almeno un carattere minuscolo.",
                checkupper: "La password deve contenere almeno un carattere Maiuscolo.",
                checkdigit: "La password deve avere almeno un numero.",
                checkspecial: "La password deve contenere almeno un carattere speciale: ={}+çò°àù§èé#@$!%€*?&:,;'._<>|-",
            },
            email2: {
                required: "Inserire l'e-mail.",
                email: "Inserire un'e-mail valida."
            },
            nome: {
                required: "Inserire il nome.",
                name: "Il nome inserito non è corretto.",
            },
            cognome: {
                required: "Inserire il cognome.",
                name: "Il cognome inserito non è corretto.",
            }
        },
        //Quando valido, ci assicuriamo che il form venga inviato
        submitHandler: function(form) {
            form.submit();
        }
    });
})
