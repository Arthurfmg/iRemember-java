var host = "http://localhost:8080/iRemember/services/usuarios/";
var baseHost = "http://localhost:8080/iRemember/";

function entrar() {
	var data = $("#formLogin").serializeJSON();
		
	$.ajax({
		url : host + 'login/' + data.email +'/'+ data.password,
		type : 'GET',
		contentType : 'application/json',
		data : data,
		success : function(data) {
			sessionStorage.setItem("usuarioLogado", JSON.stringify(data.usuario));		
			window.location.href = baseHost + "main.html";
		},
		error : function() {
			alert("Login ou senha inválidos. Tente novamente!");
		}
	});
}