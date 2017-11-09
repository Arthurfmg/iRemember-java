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

function listarDadosColecoes(){	
  $.ajax({
      url : baseHost + 'services/colecoes/ultimas',
      type : 'GET',
      success : function(data) {
    	  alert("aparentemente tá dando certo");
      },
      error : function(data) {
    	  alert("Erro ao listar últimas coleções.");
      }
   });
}

function segueLinkColecao(link){	
  $.ajax({
      url : hostColecoes + link,
      type : 'GET',
      success : function(data) {
    	  adicionaColecaoNovaAoGrid(data.colecao);
      },
      error : function(data) {
    	  alert("erro");
      }
   });
}