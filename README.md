# zghero-webcrawler - Lucas Hideki Abe
Projeto de Web Crawler, utilizando o Httpbuilder-ng para conseguir informações e baixar arquivos do Padrão TISS do governo.

<h2>ZG-HERO Project trilha (K1-T9): Web Crawler</h2>

  O projeto consistia em fazer um bot utilizando o <a href="https://http-builder-ng.github.io/http-builder-ng/asciidoc/html5/#_introduction">Httpbuilder-NG</a>
para a coleta de informações do padrão TISS na versão mais recente. A automatização de três tasks foram implementadas no script. Foram elas: </br>
  1. Baixar os arquivos do <a href="https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss">Padrão TISS</a> mais recentes. </br>
  2. Coletar o <a href="https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss/padrao-tiss-historico-das-versoes-dos-componentes-do-padrao-tiss">histórico das versões do TISS</a>, obtendo as informações da competência, publicação, início de vigência a partir de jan/2016.</br>
  3. Acessar o campo "Tabelas relacionadas" e baixar a <a href="https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss/padrao-tiss-tabelas-relacionadas">Tabela de erros no envio para a ANS.</a> </br>

Para executar o projeto, basta rodar o script e para uma melhor observação dos dados, apague o conteúdo das pastas <a href="https://github.com/hideki-abe/zghero-webcrawler/tree/master/Downloads">Downloads</a>,
<a href="https://github.com/hideki-abe/zghero-webcrawler/tree/master/VersoesTiss">Versões TISS</a> e <a href="https://github.com/hideki-abe/zghero-webcrawler/tree/master/TabelaDeErros">Tabela de Erros</a>
para uma melhor observação da inserção de dados.
