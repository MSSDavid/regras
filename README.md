# regras
Biblioteca para Execução de Regras (BER). 

<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">
<img alt="Creative Commons License" style="border-width:0"
 src="https://i.creativecommons.org/l/by/4.0/88x31.png" /></a>
 <br />This work is licensed under a <a rel="license" 
 href="http://creativecommons.org/licenses/by/4.0/">Creative Commons 
 Attribution 4.0 International License</a>. 
 <br />Fábio Nogueira de Lucena - Fábrica de Software - 
 Instituto de Informática (UFG).


## O que é uma regra?
Regra é o instrumento empregado para produzir um valor para uma dada 
entrada. A entrada é definida por um conjunto de objetos (JSON). O 
resultado é um valor numérico, uma data, um valor lógico ou uma 
sequência de caracteres. A saída também é fornecida como um objeto
JSON.

Abaixo segue uma regra do tipo expressão que soma os valores das 
variáveis "x" e "y" e o resultado é associado à variável identificada 
por "soma". A variável deve ser única para um dado conjunto de regras, 
ou seja, não é permitido outra regra que deve ser avaliada juntamente 
com aquela abaixo e também define como variável o identificador 
"soma".

`{ "tipo": "expressao", "variavel": "soma", 
   "expressao": "x + y" }`
   
Uma regra possui outros atributos, por exemplo, "valorMaximo". 
Por exemplo, enquanto a regra acima pode resultar no valor 150,
aquela abaixo terá como resultado o valor 107, pois esse é o valor
máximo indicado.

`{ "variavel": "soma", 
   "expressao": "x + y",
    "valorMaximo" : 107 }`
    
## Quais são as entradas/saídas?
As entradas são definids por objetos JSON. Os atributos desses
objetos, quaisquer que sejam eles, são tratados como variáveis. 
Por exemplo, para o objeto JSON abaixo

`{ "altura" : 1.8 }`

fornecido como entrada para a execução da regra 

`{ "variavel" : "alturaEmCentimetros", "expressao" : "altura * 100" }`

produz o valor 180, que passa a estar disponível por meio da 
variável "alturaEmCentimetros" no objeto JSON retornado

`{ "valores" : [ { "alturaEmCentimetros": 180 } ] }`
    
## Como executar um conjunto de regras?

A execução de uma regra produz um valor, associado à variável em 
questão, ou seja, um objeto JSON correspondente típico é 
ilustrado abaixo.

`{ "alturaEmCentimetros" : 180 }`

Quando um conjunto de regras é executado, temos como resultado
um conjunto de valores (objetos como aqueles acima). Um objeto 
resultado em JSON é ilustrado abaixo.

`{ "valores" : [ { "x": 1 }, { "quente": true } ] }`
 
A execução de várias regras, uma configuração, produz uma
coleção de valores, conforme ilustrado pelo método abaixo. 

```
String configuracao = // recupera conjunto de regras (JSON)
String relatos = // recupera conjunto de objetos (JSON)
String parametros = // recupera valores iniciais (JSON)
String resultado = Regras.avalia(configuracao, relatos, parametros);
```

Nesse ponto podemos ilustrar uma chamada completa para avaliação de
uma regra. 

