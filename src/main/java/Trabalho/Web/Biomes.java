package Trabalho.Web;

import java.util.List;
import java.util.Optional;

/** Dados dos biomas, na ordem em que aparecem no jogo. */
public final class Biomes {

	static final List<Biome> ALL = List.of(
			new Biome("Costa", "costa", "costa.png", 1,
					"imgImage167.webp", "imgImage11.webp",
					"O bioma Costa em PEAK é a primeira área do jogo, servindo como introdução às mecânicas de escalada e sobrevivência. Possui praias, rochas e destroços de avião, com clima ameno e desafios leves. Apresenta águas-vivas venenosas, pontes instáveis e ouriços-do-mar como principais perigos. É um ambiente de aprendizado, ideal para coletar itens básicos e se preparar para biomas mais difíceis.",
					List.of(
							food("Coco", "imgImage40.webp"),
							food("Maçã vermelha", "imgImage41.webp"),
							food("Maçã amarela", "imgImage42.webp"),
							food("Maçã verde", "imgImage43.webp"),
							food("Cogumelos em grupo", "img64PxClusterShroom1.webp",
									"img64PxClusterShroomPoisonous1.webp"),
							food("Cogumelos botão", "imgImage47.png", "img64PxButtonShroomPoisonous1.webp"),
							food("Cogumelos trombeta", "imgImage44.png", "imgImage45.png"),
							food("Cogumelos gordinhos", "img64PxChubbyShroom1.webp"),
							food("Cenoura medicinal", "imgImage138.webp")),
					List.of(
							hazard("Ouriços-do-mar gigantes",
									"Os ouriços-do-mar são armadilhas naturais nas praias da Costa. Seus espinhos podem envenenar quem se aproxima demais; mantenha distância e planeje o caminho.",
									"imgImage59.webp", "VENENO"),
							hazard("Água-viva venenosa",
									"Mesmo fora d'água, a água-viva continua perigosa. Evite tocar nela para não sofrer envenenamento durante a travessia.",
									"imgImage65.webp", "VENENO"),
							hazard("Pontes",
									"As pontes são frágeis: se mais de duas pessoas atravessarem juntas, elas podem ceder. Atravesse uma pessoa por vez, com cuidado e coordenação.",
									"imgImage70.webp", "DANO")),
					List.of()),
			new Biome("Trópico", "tropico", "tropico.png", 2,
					"imgImage167.webp", "imgImage242.webp",
					"O bioma Trópico em PEAK é a segunda área do jogo, com florestas densas, rios e chuvas frequentes. Apesar do visual vibrante, o clima úmido torna a escalada mais difícil, pois a chuva deixa as superfícies escorregadias e reduz a visibilidade. É um local ideal para coletar recursos naturais e aperfeiçoar habilidades antes de enfrentar biomas mais desafiadores.",
					List.of(
							food("Coco", "imgImage40.webp"),
							food("Maçã vermelha", "imgImage41.webp"),
							food("Maçã amarela", "imgImage42.webp"),
							food("Maçã verde", "imgImage43.webp"),
							food("Cogumelos em grupo", "img64PxClusterShroom1.webp",
									"img64PxClusterShroomPoisonous1.webp"),
							food("Cogumelos botão", "imgImage47.png", "img64PxButtonShroomPoisonous1.webp"),
							food("Cogumelos trombeta", "imgImage44.png", "imgImage45.png"),
							food("Cogumelos gordinhos", "img64PxChubbyShroom1.webp"),
							food("Cenoura medicinal", "imgImage139.png"),
							food("Pimenta", "imgImage155.webp"),
							food("Amora roxa", "imgImage158.webp"),
							food("Amora amarela", "imgImage160.webp"),
							food("Amora verde", "imgImage161.webp"),
							food("Banana amarela", "imgImage163.webp"),
							food("Banana rosa", "imgImage164.webp"),
							food("Banana azul", "imgImage165.webp"),
							food("Banana marrom", "imgImage166.webp"),
							food("Favo de mel", "imgImage211.png"),
							food("Amora vermelha", "imgImage214.webp"),
							food("Amora laranja", "imgImage215.webp"),
							food("Amora preta", "imgImage216.webp"),
							food("Carrapato", "imgCapturaDeTela20251126212803RemovebgPreview5.png")),
					List.of(
							hazard("Chuva no Trópico",
									"A chuva frequente deixa as superfícies escorregadias e reduz a visibilidade. Procure abrigo e avance com cuidado durante as tempestades.",
									"imgImage217.webp", "FRIO"),
							hazard("Monstera",
									"As folhas e raízes da monstera bloqueiam passagens e podem surpreender quem escala sem observar o terreno.",
									"imgImage253.webp", "ESPINHOS"),
							hazard("Espinhos gigantes",
									"Espinhos gigantes ocupam trechos da rota e causam ferimentos ao contato. Escolha bem os apoios e mantenha distância.",
									"imgImage254.webp", "ESPINHOS"),
							hazard("Tubos de lava",
									"Os tubos de lava escondem riscos e estreitam a passagem. Verifique a rota antes de atravessar.",
									"imgImage265.webp", "QUEIMADURA"),
							hazard("Cogumelo-bomba explosivo",
									"Este cogumelo detona quando alguém se aproxima, podendo arremessar escoteiros e interromper a escalada.",
									"imgImage220.webp", "DANO"),
							hazard("Cogumelo-bomba venenoso",
									"Uma aproximação descuidada pode liberar uma nuvem tóxica. Mantenha distância e procure um caminho seguro.",
									"imgImage74.webp", "VENENO"),
							hazard("Hera venenosa",
									"A hera cobre áreas de passagem e envenena quem entra em contato com suas folhas. Evite atravessar a vegetação.",
									"imgImage266.webp", "VENENO"),
							hazard("Vagens venenosas",
									"Vagens escondidas na vegetação podem liberar efeitos nocivos quando perturbadas. Observe o ambiente antes de avançar.",
									"imgImage267.webp", "VENENO"),
							hazard("Plantas perigosas",
									"A vegetação densa pode esconder perigos e dificultar a leitura da rota. Avance com atenção e mantenha o grupo unido.",
									"imgImage268.webp", "DANO")),
					List.of()),
			new Biome("Raízes", "raizes", "raizes.webp", 3,
					"imgImage167.webp", "titulo.webp",
					"O bioma Raízes em PEAK é uma das duas opções para o segundo trecho da escalada, no lugar do Trópico. É uma floresta de sequoias gigantes tomada por cogumelos, com plataformas de madeira, cipós e pontes ligando as copas. O vento sopra em rajadas e empurra os escoteiros para os lados, enquanto nuvens de esporos, aranhas e besouros tornam cada travessia mais arriscada. Acender a fogueira no topo libera o caminho para o próximo bioma.",
					List.of(
							food("Shroomberry vermelho", "/images/items/food/naturais/shroomberry-vermelho.webp"),
							food("Shroomberry amarelo", "/images/items/food/naturais/shroomberry-amarelo.webp"),
							food("Shroomberry verde", "/images/items/food/naturais/shroomberry-verde.webp"),
							food("Shroomberry azul", "/images/items/food/naturais/shroomberry-azul.webp"),
							food("Shroomberry roxo", "/images/items/food/naturais/shroomberry-roxo.webp"),
							food("Cenoura medicinal", "/images/biome-pages/costa/imgImage138.webp"),
							food("Mandrágora", "/images/items/food/naturais/mandragora.webp"),
							food("Favo de mel", "/images/items/food/naturais/favo-de-mel.webp"),
							food("Cogumelos em grupo", "/images/biome-pages/costa/img64PxClusterShroom1.webp",
									"/images/biome-pages/costa/img64PxClusterShroomPoisonous1.webp"),
							food("Cogumelos botão", "/images/items/food/cogumelos/imgImage204.webp",
									"/images/biome-pages/costa/img64PxButtonShroomPoisonous1.webp"),
							food("Cogumelos trombeta", "/images/items/food/cogumelos/imgImage201.webp",
									"/images/items/food/cogumelos/imgImage202.webp"),
							food("Cogumelos gordinhos", "/images/biome-pages/costa/img64PxChubbyShroom1.webp")),
					List.of(
							hazard("Vento",
									"Rajadas de vento sopram de tempos em tempos e empurram escoteiros e objetos para os lados. Enquanto o vento sopra, ficar parado agarrado à parede gasta tanta energia quanto escalar. Espere a rajada passar atrás de árvores ou rochas antes de atravessar trechos arriscados.",
									"vento.webp", "ENERGIA"),
							hazard("Pontes",
									"Pontes ligam algumas plataformas das sequoias, mas cada uma aguenta um número limitado de escoteiros, de 1 a 5. Se o limite for atingido, ela quebra depois de alguns segundos. Atravesse em grupos pequenos e evite fazer isso durante o vento.",
									"pontes.webp", "DANO"),
							hazard("Nuvens de esporos",
									"Uma névoa rosa e brilhante, liberada por um emissor no centro, causa esporos enquanto o escoteiro estiver dentro dela. O vento dispersa a nuvem por um tempo, então aproveite as rajadas para passar.",
									"nuvem-de-esporos.webp", "ESPOROS"),
							hazard("Cogumelos saltitantes malignos",
									"Parecem os cogumelos saltitantes comuns, mas são roxos e rachados. Eles também arremessam o escoteiro para cima, só que causam 5 de esporos a cada salto.",
									"cogumelo-saltitante-maligno.webp", "ESPOROS"),
							hazard("Cogumelo-bomba de esporos",
									"Fica no chão, principalmente perto das bordas, e explode ao contato, soltando uma nuvem roxa que causa esporos. A nuvem some depois de um tempo ou quando o vento sopra.",
									"cogumelo-bomba-de-esporos.webp", "ESPOROS"),
							hazard("Cogumelo-bomba explosivo",
									"Amarelo com manchas verdes, ele explode ao contato e arremessa o escoteiro na direção de onde veio. Também aparece no Trópico.",
									"cogumelo-bomba-explosivo.webp", "DANO"),
							hazard("Besouros",
									"Os besouros perseguem os escoteiros e tentam arremessá-los. Não causam dano sozinhos, mas uma queda pode ser fatal. Um som avisa quando há besouros por perto.",
									"besouros.webp", "ARREMESSO"),
							hazard("Aranhas",
									"Ficam penduradas em árvores e penhascos e agarram quem passa por baixo, causando 25 de teia e bastante veneno. Arremessar um objeto na aranha a atordoa e permite passar em segurança.",
									"aranhas.webp", "TEIA"),
							hazard("Zumbis",
									"A partir da ascensão Pico, zumbis perseguem e escalam atrás dos escoteiros, mordendo e causando esporos e ferimentos. Eles só reagem quando veem alguém, e acender a fogueira os elimina.",
									"zumbis.webp", "ESPOROS")),
					List.of(),
					List.of(
							feature("Sequoias",
									"Árvores de tronco grosso e copa em cone, com plataformas de madeira para se apoiar. Nelas podem ser encontrados fungos-plataforma, fungos-nuvem e fungos saltitantes, e as copas servem de caminho para avançar.",
									"sequoias.webp"),
							feature("Tocos",
									"Tocos de sequoia podem ser maciços ou ocos. Os ocos têm entrada em cima e embaixo e às vezes guardam um favo de mel.",
									"tocos.webp"),
							feature("Cipós",
									"Ligam pontos da montanha. Agarrar um cipó gasta pouquíssima energia e se mover por ele custa bem menos que escalar. Às vezes há shroomberries crescendo neles.",
									"cipos.webp"),
							feature("Fungos-prateleira gigantes",
									"Fungos enormes presos às árvores e às paredes da escalada, que funcionam como plataformas para descansar.",
									"fungos-prateleira.webp"),
							feature("Cogumelos saltitantes",
									"Espalhados por todo o bioma, arremessam o escoteiro para cima quando ele cai sobre eles e recuperam um pouco de energia.",
									"cogumelos-saltitantes.webp"),
							feature("Água",
									"A água cobre as partes mais baixas do bioma e deixa o escoteiro mais lento enquanto ele caminha por ela.",
									"agua.webp"),
							feature("Cachoeiras",
									"Como no Trópico, atrapalham um pouco a visão, e tocar na água derruba o escoteiro por alguns segundos, sem causar dano.",
									"cachoeiras.webp"))),
			new Biome("Alpes", "alpes", "alpes.webp", 4,
					"imgImage167.webp", "imgImage74.webp",
					"O bioma Alpes em PEAK é a terceira área do jogo, com picos cobertos de neve, geleiras perigosas e cristas rochosas expostas. Apesar da vista espetacular e da neve intocada, o clima gelado torna a escalada mais difícil, pois o gelo e a neve compactada deixam as superfícies escorregadias e o frio intenso exige gerenciamento constante de recursos. É um local para aperfeiçoar habilidades de escalada em gelo e gerenciamento de temperatura antes de enfrentar biomas mais desafiadores.",
					List.of(
							food("Laranja", "imgImage284.webp"),
							food("Laranja amarela", "imgImage285.webp"),
							food("Napberry", "imgImage283.webp")),
					List.of(
							hazard("Tempestade de neve",
									"A nevasca reduz drasticamente a visibilidade e causa frio rapidamente. Procure abrigo antes que o mau tempo comprometa a escalada.",
									"imgImage253.webp", "FRIO"),
							hazard("Pingentes de gelo",
									"Os pingentes podem tremer e cair quando escalados. Avalie a estabilidade do gelo antes de usá-lo como apoio.",
									"imgImage254.webp", "DANO"),
							hazard("Pedras de gelo",
									"O contato com as pedras de gelo causa frio e pode tornar a progressão lenta. Prepare-se para manter a temperatura corporal.",
									"imgImage265.webp", "FRIO"),
							hazard("Vagens de luz",
									"Quando ativadas, as vagens liberam uma névoa que cobre a visão dos escoteiros próximos e torna a escalada perigosa.",
									"imgImage293.webp", "CEGUEIRA"),
							hazard("Gêiser",
									"O vapor do gêiser pode surpreender quem passa perto. Espere o momento seguro para atravessar a área.",
									"imgImage294.webp", "DANO")),
					List.of()),
			new Biome("Mesa", "mesa", "mesa.png", 5,
					"imgImage167.webp", "imgImage74.webp",
					"O bioma Mesa em PEAK é uma região de cânions profundos, plataformas rochosas elevadas e um céu vasto e árido. O clima seco e turbulento torna a escalada mais difícil, pois tornados aparecem espontaneamente e podem arremessar os escoteiros para longe. É um local ideal para aperfeiçoar habilidades de esquiva e gerenciamento de altitude.",
					List.of(
							food("Coco", "imgImage40.webp"),
							food("Laranja amarela", "imgImage285.webp"),
							food("Cogumelos botão", "imgImage47.png", "img64PxButtonShroomPoisonous1.webp"),
							food("Cogumelos trombeta", "imgImage44.png", "imgImage45.png"),
							food("Cogumelos em grupo", "img64PxClusterShroom1.webp",
									"img64PxClusterShroomPoisonous1.webp"),
							food("Cogumelos gordinhos", "img64PxChubbyShroom1.webp"),
							food("Espinheiro dourado", "imgImage296.webp"),
							food("Espinheiro vermelho", "imgImage295.webp")),
					List.of(
							hazard("Sol",
									"A exposição direta ao sol causa queimadura e superaquecimento. Procure sombra e controle os recursos de calor durante a travessia.",
									"imgImage217.webp", "QUEIMADURA"),
							hazard("Cactos",
									"O contato com cactos causa espinhos e pode prender o escoteiro. Evite as plantas e ajude aliados que ficarem presos.",
									"imgImage253.webp", "ESPINHOS"),
							hazard("Capins do deserto",
									"Os capins secos rolam pelo mapa e podem derrubar escoteiros desatentos, causando espinhos e arremessando-os para longe.",
									"imgImage254.webp", "ESPINHOS"),
							hazard("Tornado",
									"Os tornados surgem repentinamente e arremessam escoteiros para longe. Observe o céu e desvie assim que possível.",
									"imgImage265.webp", "DANO"),
							hazard("Formiga-leão",
									"A areia pode esconder formigas-leão que atacam quem se aproxima. Observe o solo antes de cruzar áreas arenosas.",
									"imgImage267.webp", "DANO"),
							hazard("Escorpiões",
									"Nas cavernas da Mesa, escorpiões atacam com uma picada venenosa. Explore as passagens subterrâneas com cautela.",
									"imgImage266.webp", "VENENO"),
							hazard("Dinamite",
									"A dinamite acende quando um escoteiro se aproxima e explode pouco depois, causando dano e empurrando jogadores próximos.",
									"imgImage220.webp", "DANO")),
					List.of()),
			new Biome("Caldeira", "caldeira", "caldeira.webp", 6,
					"imgImage167.webp", "imgImage74.webp",
					"O bioma Caldeira em PEAK é uma área de alto nível do jogo, com rochas vulcânicas escuras, rios de lava incandescentes e uma atmosfera sufocante. O calor escaldante e a atividade sísmica tornam a escalada extremamente difícil, pois o chão de lava causa queimaduras e a fumaça vulcânica reduz a visibilidade.",
					List.of(
							food("Ovo", "imgImage201.webp"),
							food("Frango", "imgImage297.webp")),
					List.of(
							hazard("Lava",
									"A lava sobe progressivamente com o tempo e pode causar queimaduras graves. Continue a escalada e evite cair nas áreas incandescentes.",
									"imgImage217.webp", "QUEIMADURA"),
							hazard("Erupções",
									"Rochas começam a brilhar antes de irromper em chamas. Afaste-se das áreas aquecidas para evitar queimaduras.",
									"imgImage298.png", "QUEIMADURA"),
							hazard("Cogumelo-bomba venenoso",
									"Uma aproximação descuidada pode liberar veneno. Observe o terreno e mantenha distância do cogumelo.",
									"imgImage220.webp", "VENENO")),
					List.of()),
			new Biome("Melancolia", "melancolia", "melancolia.webp", 7,
					"imgImage167.webp", "titulo.webp",
					"O bioma Melancolia em PEAK é um pântano assombrado coberto por uma névoa roxa, de onde se veem fantasmas subindo. É uma das duas opções para o quarto trecho da escalada, no lugar da Caldeira, e é o único caminho até a Cidadela, a torre gigante que domina a paisagem. A névoa causa sonolência em quem fica dentro dela por muito tempo, então a travessia é feita pulando entre ilhas, árvores e cipós. Acender a fogueira no salão de entrada da Cidadela dissipa a barreira de névoa e libera o próximo bioma.",
					List.of(
							food("Ovo", "/images/biome-pages/caldeira/imgImage201.webp"),
							food("Frango", "/images/biome-pages/caldeira/imgImage297.webp"),
							food("Minhoca Madrugadora", "/images/items/food/naturais/minhoca-madrugadora.webp"),
							food("Cachorro-quente", "/images/items/food/naturais/cachorro-quente.webp"),
							food("Banana amarela", "/images/biome-pages/tropico/imgImage163.webp"),
							food("Pernas de sapo", "/images/items/food/naturais/pernas-de-sapo.webp")),
					List.of(
							hazard("Névoa sonolenta",
									"O principal perigo do bioma: uma névoa roxa cobre quase tudo, menos as pequenas ilhas e as árvores gigantes. Dentro dela, o escoteiro ganha 2,5 de sonolência a cada 2,75 segundos, então planeje a rota antes de descer. Qualquer fonte de luz acesa afasta a névoa ao redor.",
									"nevoa-sonolenta.webp", "SONOLÊNCIA"),
							hazard("Água da Melancolia",
									"A água do pântano reduz a velocidade de caminhada pela metade e diminui muito a força dos pulos. As vitórias-régias são o caminho seguro para atravessar.",
									"agua-da-melancolia.webp", "LENTIDÃO"),
							hazard("Plantas carnívoras",
									"Plantas paradas no chão que prendem quem pisa nelas abertas. Presos, os escoteiros recebem veneno ao longo do tempo e ficam com a voz abafada. Dá para escapar com energia suficiente ou com a ajuda de um amigo.",
									"plantas-carnivoras.webp", "VENENO"),
							hazard("Fantasma gigante",
									"Aparece quando os escoteiros ficam tempo demais na névoa. Depois de um som de aviso, ele persegue o escoteiro mais próximo atravessando o terreno e explode ao encostar, causando frio, cegueira e um arremesso que pode ferir na queda. Castiçais, lanternas de fada e sinos acesos queimam o fantasma.",
									"fantasma-gigante.webp", "FRIO"),
							hazard("Sapos",
									"Quando um escoteiro entra no campo de visão de um sapo, ele dispara a língua de longe e puxa o escoteiro para perto, derrubando-o. Sapos podem ser carregados e arremessados, mas não guardados, e viram pernas de sapo quando cozidos.",
									"sapos.webp", "DANO"),
							hazard("Corvos trapaceiros",
									"Pousados em placas e ninhos, depois de um tempo na névoa eles roubam a voz de um escoteiro: a fala passa a sair do corvo, até o escoteiro deixar a Melancolia ou o corvo voar. Eles se assustam e voam quando alguém chega perto.",
									"corvos-trapaceiros.webp", "VOZ"),
							hazard("Grades de ferro",
									"Grades de ferro fundido bloqueiam o caminho direto até a próxima ilha. Não podem ser escaladas, e tocar no topo causa 2,5 de ferimento por segundo.",
									"grades-de-ferro.webp", "DANO")),
					List.of(),
					List.of(
							feature("Sinos",
									"Torres com sino ficam dentro da névoa. Acender uma delas afasta a névoa ao redor para sempre. Acender cinco na mesma expedição concede a Insígnia do Sineiro.",
									"sinos.webp"),
							feature("Minhoca Madrugadora",
									"Uma minhoca que anda em círculos pelo bioma até ser pega. Comê-la remove a sonolência e concede a Insígnia do Bem Descansado.",
									"minhoca-madrugadora.webp"),
							feature("Vitórias-régias",
									"Folhas que flutuam na água do pântano. Ficar em cima delas evita a lentidão causada pela água.",
									"vitorias-regias.webp"),
							feature("Escoteiros petrificados",
									"Estátuas parecidas com escoteiros que apontam para uma bagagem, que pode estar acima ou dentro da névoa.",
									"escoteiros-petrificados.webp"),
							feature("Ninhos de corvo",
									"Ficam em árvores e pilares. Os corvos pousados neles não atacam, e cada ninho pode ter até cinco ovos pequenos.",
									"ninhos-de-corvo.webp"),
							feature("Cipós",
									"Ligam partes do terreno e podem ser agarrados como correntes, gastando pouca energia. Às vezes há bananas amarelas penduradas neles.",
									"cipos.webp"),
							feature("Entrada da Cidadela",
									"Uma escadaria em ruínas leva ao salão de entrada, com a fogueira no centro. À esquerda há uma sala com uma estátua anciã ou um item místico; à direita, três bagagens protegidas por uma armadilha de flechas, que exige passar agachado.",
									"entrada-da-cidadela.webp"))),
			new Biome("O Forno", "o-forno", "forno.png", 8,
					"imgImage167.webp", "imgImage74.webp",
					"O bioma Forno em PEAK é a área mais quente do jogo, com rochas vulcânicas avermelhadas, gêiseres de vapor e uma atmosfera sufocante. O calor opressor e os gases tóxicos tornam a escalada extremamente difícil: o chão quente causa dano de fogo e o vapor exige cautela para evitar queimaduras.",
					List.of(),
					List.of(
							hazard("Rochas quentes",
									"Rochas quentes ficam embutidas nas paredes do bioma e causam queimaduras ao contato ou quando o escoteiro permanece muito perto.",
									"imgImage217.webp", "QUEIMADURA"),
							hazard("Lava",
									"A lava sobe progressivamente e aquece as rochas próximas. Mantenha a progressão vertical para escapar do calor.",
									"imgImage303.webp", "QUEIMADURA")),
					List.of()),
			new Biome("Cidadela", "cidadela", "cidadela.webp", 9,
					"imgImage167.webp", "titulo.webp",
					"O bioma Cidadela em PEAK é o interior de uma torre altíssima, cheia de armadilhas e estruturas em ruínas. Ela só aparece depois da Melancolia e ocupa o quinto trecho da escalada, no lugar do Forno. A torre tem um pilar central, mais fácil de escalar mas com mais armadilhas e bagagens, e uma parede externa quase sem armadilhas, mas com menos apoios. Não há fogueira nem comida natural no caminho: o topo leva direto ao Pico. A partir da ascensão Pico, a névoa sonolenta sobe da base da torre e obriga o grupo a se apressar.",
					List.of(),
					List.of(
							hazard("Armadilhas de flecha",
									"Ficam nas paredes e no teto. Tocar na linha azul dispara uma flecha que causa 7,5 de flecha, e arrancá-la causa 12,5 de ferimento. A armadilha desliga depois de três disparos.",
									"armadilhas-de-flecha.webp", "FLECHA"),
							hazard("Armadilhas de espinhos",
									"Placas de pressão no chão que armam em 0,8 segundo, soltam espinhos e recolhem depois de cerca de um segundo, causando 15 de ferimento.",
									"armadilhas-de-espinhos.webp", "DANO"),
							hazard("Maças balançantes",
									"Grandes bolas de espinhos que balançam sem parar sobre as plataformas. Um golpe causa até 25 de ferimento e derruba o escoteiro.",
									"macas-balancantes.webp", "DANO"),
							hazard("Serras circulares",
									"Serras que sobem e descem por trilhos nas paredes, cortando pisos, escadas e janelas. O contato causa ferimento.",
									"serras-circulares.webp", "DANO"),
							hazard("Bagagens-armadilha",
									"Parecem bagagens comuns com a alça levantada e a tampa entreaberta. Ao serem abertas, soltam uma nuvem de gás que causa sonolência.",
									"bagagens-armadilha.webp", "SONOLÊNCIA"),
							hazard("Pedras petrificantes",
									"Rochas presas às paredes que causam 2 de petrificação por segundo enquanto o escoteiro estiver encostado nelas.",
									"pedras-petrificantes.webp", "PETRIFICAÇÃO"),
							hazard("Rolos de espinhos verticais",
									"Vigas de metal cheias de espinhos que giram no centro da torre e causam 5 de ferimento a cada 0,25 segundo de contato.",
									"rolos-de-espinhos-verticais.webp", "DANO"),
							hazard("Rolos de espinhos horizontais",
									"Dois ou quatro trituradores com espinhos giram em volta de uma plataforma elevada, causando 5 de ferimento a cada 0,25 segundo. Às vezes há uma bagagem no centro.",
									"rolos-de-espinhos-horizontais.webp", "DANO"),
							hazard("Caldeirão de lava",
									"Uma bacia dourada com uma fina camada de lava no centro do segundo andar. Encostar nela causa queimadura. Jogar o Ídolo Ancião ali também vale para a Insígnia 24 Quilates.",
									"caldeirao-de-lava.webp", "QUEIMADURA")),
					List.of(),
					List.of(
							feature("Correntes douradas",
									"Correntes espalhadas pela torre, como as da Caldeira, que podem ser usadas para subir.",
									"correntes-douradas.webp"),
							feature("Escadas douradas",
									"Escadas de mão douradas que gastam menos energia e permitem subir mais rápido.",
									"escadas-douradas.webp"),
							feature("Lâmpadas",
									"Pequenas fontes de luz em aros dourados presos às paredes, que também servem de apoio para escalar.",
									"lampadas.webp"),
							feature("Estátuas de escoteiros",
									"Estátuas douradas distribuídas pelos andares, algumas caídas. Dá para escalar nelas.",
									"estatuas-de-escoteiros.webp"),
							feature("Escadarias",
									"Escadarias de pedra em diferentes estados de ruína, mais inteiras no topo e na base. Podem ser percorridas a pé ou escaladas.",
									"escadarias.webp"),
							feature("Topo da torre",
									"O último andar é uma plataforma desgastada cercada por estátuas douradas, incluindo uma do Chefe Escoteiro. Um arco com uma estrela no alto leva ao Pico.",
									"topo-da-torre.webp"))),
			new Biome("Pico", "pico", "pico.webp", 10,
					"imgImage167.webp", "imgImage74.webp",
					"O Pico em PEAK é o estágio final do jogo, caracterizado por uma subida vertical final, um campo de flores vibrante e um grande sol preenchendo o céu. A área exige a gestão perfeita dos últimos recursos para manter a stamina. É o local ideal para focar na precisão de cada agarre e usar a resistência mental para alcançar o ponto final e a glória da escalada.",
					List.of(), List.of(),
					List.of(
							new ArrivalSection(
									"Após a escalada extenuante e a superação dos biomas mais desafiadores, a jornada atinge seu clímax no Pico. A visão no topo é recompensadora, com um campo de flores vibrante e um grande sol dominando a paisagem.",
									"imgImage299.webp", "Bandeira azul no campo de flores do Pico"),
							new ArrivalSection(
									"No ponto mais alto, a recompensa é a Bandeira do Escoteiro. Ao lado dela, uma caixa de resgate contém o sinalizador que finaliza a expedição. Após a contagem regressiva, todos os escoteiros sobreviventes aparecem juntos para celebrar a chegada.",
									"imgImage300.webp", "Escoteiros celebrando a chegada ao Pico"))));

	private Biomes() {
	}

	static Optional<Biome> bySlug(String slug) {
		return ALL.stream().filter(biome -> biome.slug().equals(slug)).findFirst();
	}

	private static Food food(String name, String... images) {
		return new Food(name, List.of(images));
	}

	private static Feature feature(String title, String description, String image) {
		return new Feature(title, description, image);
	}

	private static Hazard hazard(String title, String description, String image, String effect) {
		String effectClass = switch (effect) {
			case "VENENO" -> "poison";
			case "ESPINHOS" -> "thorns";
			case "FRIO", "CEGUEIRA" -> "cold";
			case "QUEIMADURA" -> "burn";
			case "ESPOROS" -> "spores";
			case "SONOLÊNCIA" -> "sleep";
			case "TEIA" -> "web";
			case "PETRIFICAÇÃO" -> "petrify";
			case "FLECHA" -> "arrow";
			case "ENERGIA" -> "energy";
			case "ARREMESSO", "LENTIDÃO", "VOZ" -> "neutral";
			default -> "damage";
		};
		return new Hazard(title, description, image, effect, effectClass);
	}

	public record Biome(String name, String slug, String image, int number, String backdrop, String banner,
			String description, List<Food> foods, List<Hazard> hazards, List<ArrivalSection> arrival,
			List<Feature> features) {
		/** Biomas sem a seção de estruturas. */
		public Biome(String name, String slug, String image, int number, String backdrop, String banner,
				String description, List<Food> foods, List<Hazard> hazards, List<ArrivalSection> arrival) {
			this(name, slug, image, number, backdrop, banner, description, foods, hazards, arrival, List.of());
		}

		/** Caminhos que começam com "/" já são absolutos; os demais ficam na pasta do bioma. */
		public String imagePath(String image) {
			return ImagePaths.canonicalize(image.startsWith("/") ? image : "/images/biome-pages/" + slug + "/" + image);
		}

		public String cardImagePath() {
			return ImagePaths.canonicalize("/images/biomes/" + image);
		}

		public List<String> titleOverlays() {
			return slug.equals("pico")
					? List.of("imgRectangle7.webp", "imgRectangle8.svg")
					: List.of("imgRectangle7.svg");
		}

		public List<FoodEffect> foodEffects() {
			return switch (slug) {
				case "costa" -> List.of(
						new FoodEffect("O alimento causa efeito de veneno", "poison"),
						new FoodEffect("O alimento proporciona energia extra", "energy"),
						new FoodEffect("O alimento causa dano", "damage"),
						new FoodEffect("O alimento causa efeito de queimadura", "burn"));
				case "tropico" -> List.of(
						new FoodEffect("O alimento causa efeito de veneno", "poison"),
						new FoodEffect("O alimento proporciona energia extra", "energy"),
						new FoodEffect("O alimento causa efeito de espinhos", "thorns"),
						new FoodEffect("O alimento causa dano", "damage"));
				case "alpes" -> List.of(
						new FoodEffect("O alimento causa efeito de sonolência", "sleep"),
						new FoodEffect("O alimento proporciona energia extra", "energy"));
				case "mesa" -> List.of(
						new FoodEffect("O alimento causa efeito de veneno", "poison"),
						new FoodEffect("O alimento proporciona energia extra", "energy"),
						new FoodEffect("O alimento causa efeito de espinhos", "thorns"));
				case "melancolia" -> List.of(
						new FoodEffect("O alimento proporciona energia extra", "energy"));
				case "raizes" -> List.of(
						new FoodEffect("O alimento causa efeito de veneno", "poison"),
						new FoodEffect("O alimento proporciona energia extra", "energy"),
						new FoodEffect("O alimento causa efeito de esporos", "spores"));
				default -> List.of();
			};
		}
	}

	public record Food(String name, List<String> images) {
	}

	public record FoodEffect(String label, String cssClass) {
	}

	public record Hazard(String title, String description, String image, String effect, String effectClass) {
	}

	public record Feature(String title, String description, String image) {
	}

	public record ArrivalSection(String description, String image, String alt) {
	}
}
