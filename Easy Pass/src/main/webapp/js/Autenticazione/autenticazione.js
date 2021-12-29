$(document).ready(function (){
    $('#sign-in').click(function (){
        $('#login-in').addClass('none')
        $('#login-up').removeClass("none")
    });

    $('#sign-up').click(function (){
        $('#login-up').addClass('none')
        $('#login-in').removeClass('none')
    });

    $.validator.addMethod("email_unisa", function (value){
        return /^[a-zA-Z0-9_.]+@(?:(?:[a-zA-Z0-9-]+\.)?[a-zA-Z]+\.)?(unisa)\.it$/.test(value);
    }, "Inserisci l'e-mail di ateneo.");

    $.validator.addMethod("strong_password", function (value) {
        return /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[=^ ì{}+çò°àù§èé#@$!%€*?&:,;'._<>|-])[A-Za-z\d=^ ì{}+çò°àù§èé#@$!%€*?&:,;'._<>|-]{8,20}$/.test(value)
    }, "La password è errata.");

    $.validator.addMethod("checklower", function (value){
       return /^(?=.*[a-z])/.test(value);
    }, "La password deve contenere almeno un carattere minuscolo.");

    $.validator.addMethod("checkupper", function (value){
        return /^(?=.*[A-Z])/.test(value);
    }, "La password deve contenere almeno un carattere Maiuscolo.");

    $.validator.addMethod("checkdigit", function (value){
        return /^(?=.*[0-9])/.test(value);
    },"La password deve avere almeno un numero.");

    $.validator.addMethod("checkspecial", function (value){
        return /^(?=.*[={}+çò°àù§èé#@$!%€*?&:,;'._<>|-])/.test(value);
    }, "La password deve contenere almeno un carattere speciale: ={}+çò°àù§èé#@$!%€*?&:,;'._<>|-");

    $.validator.addMethod("name_surname", function (value) {
        return /^[a-zA-Z .']+$/.test(value)
    }, "Il nome inserito non è corretto.");

    $("form[name='admin-login']").validate({
        rules: {
            email: {
                required: true,
                email: true,
                email_unisa: true
            },
            password: {
                required: true,
                strong_password: true,
            }
        },
        messages: {
            password: {
                required: "Inserire la password.",
            },
            email: {
                required: "Inserire l'e-mail.",
                email: "Inserire un'e-mail valida.",
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
                name_surname: true,
            },
            cognome: {
                required: true,
                name_surname: true,
            },
            dipartimento:{
                required: true,
            },
            email2: {
                required: true,
                email: true,
                email_unisa: true
            },
            password2: {
                required: true,
                checklower: true,
                checkupper: true,
                checkdigit: true,
                checkspecial: true,
                minlength: 8,
                maxlength: 20,
            }
        },
        messages: {
            password2: {
                required: "Inserire la password.",
                minlength: "La password deve essere almeno di 8 caratteri.",
                maxlength: "La password deve essere almeno di 20 caratteri."
            },
            email2: {
                required: "Inserire l'e-mail.",
                email: "Inserire un'e-mail valida.",
            },
            nome: {
                required: "Inserire il nome.",
            },
            cognome: {
                required: "Inserire il cognome.",
            },
            dipartimento: {
                required: "Inserire il Dipartimento.",
            },
        },
        submitHandler: function(form) {
            form.submit();
        }
    });
})
