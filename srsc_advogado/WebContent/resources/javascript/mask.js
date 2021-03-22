 jQuery(function($){
    $("#date").mask("99/99/9999");
    $("#telefone").mask("(99) 9999-9999");
    $("#celular").mask("(99) 99999-9999");
    $("#cep").keyup(function(){
    	if ($(this).length > 4)
    		$(this).val($(this).val()+"-");
    });
    $("#cpf").mask("999.999.999-99");
    $("#cnpj").mask("99.999.999/9999-99");
 });