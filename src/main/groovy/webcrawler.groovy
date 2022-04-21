import com.opencsv.CSVWriter
import org.jsoup.nodes.Document
import groovyx.net.http.optional.Download

import static groovyx.net.http.HttpBuilder.configure

//TASK 1
Document page = configure {
    request.uri = 'https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss/padrao-tiss-2013-marco-2022'
}.get() as Document

def hrefs = page.getElementsByAttributeValueContaining("class", 'table table-bordered')
        .collect{
            it.getElementsByAttributeValueContaining("class", "btn btn-primary btn-sm center-block internal-link").eachAttr("href")
        }

String link1 = hrefs.toString().split(",")[0].replace("[", "")
String link2 = hrefs.toString().split(",")[1].replace(" ", "")
String link3 = hrefs.toString().split(",")[2].replace(" ", "")
String link4 = hrefs.toString().split(",")[3].replace("]", "").replace(" ", "")

def downloads = new ArrayList()
downloads.add(link1)
downloads.add(link2)
downloads.add(link3)
downloads.add(link4)

def download(String link){
    File file = configure {
        request.uri = link
    }.get {
        Download.toFile(delegate, new File('../../../Downloads/files.zip'))
    }
}
downloads.each {file -> download(file)}

//TASK 2 - Histórico das versões dos Componentes do Padrão TISS
Document hvcpt = configure {
    request.uri = 'https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-' +
            '2013-tiss/padrao-tiss-historico-das-versoes-dos-componentes-do-padrao-tiss'
}.get() as Document

String table = hvcpt.getElementsByAttributeValueContaining("id", "parent-fieldname-text")
        .collect{
            it.getElementsByTag('td').join(", ").replace("<td>", "").replace("</td>", "")
        }

def dados =  table.replace("[", "").replace("]", "")split(", ")


class Versao {
    String competencia
    String publicacao
    String inicioDeVigencia

    Versao(String competencia, String publicacao, String inicioDeVigencia) {
        this.competencia = competencia
        this.publicacao = publicacao
        this.inicioDeVigencia = inicioDeVigencia
    }

    @Override
    public String toString() {
        return "versao{" + '\n' +
                "competencia= " + competencia + '\n' +
                "publicacao= " + publicacao + '\n' +
                "inicioDeVigencia= " + inicioDeVigencia + '\n' +
                '}';
    }
}

List<Versao> listaDeVersoes = new ArrayList<>()
String compInicial = null
int index = 0
while(!compInicial.equals("jan/2016")){
    String comp = dados[index]
    String publi = dados[index + 1]
    String versao = dados[index + 2]

    listaDeVersoes.add(new Versao(comp, publi, versao))
    index += 9
    compInicial = comp
}
def persistencia(List<Versao> list){
    try{
        FileWriter fw = new FileWriter(new File("../../../VersoesTiss/versoes.csv"))
        CSVWriter cw = new CSVWriter(fw)

        String[] headers = ["Competencia", "Publicacao",  "Inicio de Vigencia"]

        List<String[]> data = new ArrayList<String[]>();
        data.add(headers);
        list.forEach({ versao ->
            String[] item = [versao.getCompetencia(), versao.getPublicacao(), versao.getInicioDeVigencia()];
            data.add(item);
        });

        cw.writeAll(data);
        cw.close();
        fw.close();

    } catch (Exception e){
        e.printStackTrace()
    }

}

persistencia(listaDeVersoes)

//TASK 3 - TABELAS DE ERROS NO ENVIO PARA A ANS
Document tabelasRelacionadas = configure {
    request.uri = 'https://www.gov.br/ans/pt-br/assuntos/prestadores/padrao-para-troca-de-informacao-de-saude-suplementar-2013-tiss/padrao-tiss-tabelas-relacionadas'
}.get() as Document

def href = tabelasRelacionadas.getElementsByAttributeValueContaining("id", "parent-fieldname-text")
        .collect {
            it.getElementsByAttributeValueContaining("class", "internal-link").attr("href")
        }

def downloadTabelaDeErros(String link){
    File file = configure {
        request.uri = link
    }.get {
        Download.toFile(delegate, new File('../../../TabelaDeErros/files.xlsx'))
    }
}

downloadTabelaDeErros(href)

