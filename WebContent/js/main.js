var host = "http://localhost:8080/iRemember/services/usuarios/";
var baseHost = "http://localhost:8080/iRemember/";

function exibirNomeUsuarioLogado() {
	var usuarioLogado = JSON.parse(sessionStorage.getItem("usuarioLogado"));
	
	$("#logadoNome").html(usuarioLogado.nome);
}

function sair() {
	sessionStorage.removeItem("usuarioLogado");		
	window.location.href = baseHost + "login.html";
}