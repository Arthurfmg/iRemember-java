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



function listarDadosSeries(){	
  $.ajax({
      url : baseHost + 'services/series/resumos',
      type : 'GET',
      success : function(data) {
    	  var seriesResumos = data.seriesResumos.seriesresumos;
    	      	  
    	  if ($.isArray(seriesResumos)) {
			   for ( var i = 0; i < seriesResumos.length; i++) {
				   var serieResumo = seriesResumos[i];
				   
				   $("#seriesResumosGrid").append(
					'<tr>'+
						'<td>'+serieResumo.serieNome+'</td>'+
						'<td>'+'Você parou na ' + serieResumo.numeroTemporadaParou + 'º temporada, no ' + serieResumo.numeroCapituloParou + 'º capítulo</td>'+
					'</tr>'
				   );
			   }
			}
      },
      error : function(data) {
    	  alert("Erro ao listar resumos das séries.");
      }
   });
}


function listarDadosColecoes(){	
  $.ajax({
      url : baseHost + 'services/colecoes/ultimas',
      type : 'GET',
      success : function(data) {
    	  var colecoesUnidades = data.colecoesUnidades.colecoesunidades;
    	      	  
    	  if ($.isArray(colecoesUnidades)) {
			   var colecaoAtual;
			   var linha;

			   for ( var i = 0; i < colecoesUnidades.length; i++) {
				   var colecaoUnidade = colecoesUnidades[i];
				   
				   
				   if(colecaoAtual != colecaoUnidade.colecaoNome){
					   if(colecaoAtual != undefined){
						   linha = linha.substr(0, (linha.length - 1));
						   linha += '</td></tr>';
						   
						   $("#colecoesUnidadesGrid").append(linha);
					   }
					   colecaoAtual = colecaoUnidade.colecaoNome;

					   linha = '<tr><td>' + colecaoAtual + '</td><td>Você possui as unidades: ';
					   linha += colecaoUnidade.numero + ',';
				   }else{
					   linha += colecaoUnidade.numero + ',';
				   }
			   }
			   
			   linha = linha.substr(0, (linha.length - 1));
			   linha += '</td></tr>';
			   $("#colecoesUnidadesGrid").append(linha);
			}
      },
      error : function(data) {
    	  alert("Erro ao listar últimas coleções.");
      }
   });
}
