package Trabalho.Web;

import static Trabalho.Web.ItemCatalogs.gear;

import java.util.List;

import Trabalho.Web.ItemCatalogs.ItemCatalog;
import Trabalho.Web.ItemCatalogs.ItemSection;

/** Catálogo de equipamentos (dados conforme a PEAK Wiki). */
public final class EquipmentCatalog {

	private static final String CURE_ALL = "Cura a maioria dos efeitos negativos, inclusive um pouco de maldição (3 usos).";
	private static final String FAERIE_LANTERN = "Acesa, remove ao longo de 30 s calor, frio, veneno, esporos, sonolência e ferimento de quem estiver perto. Queima o Fantasma gigante da Melancolia.";
	private static final String SCOUT_RELIC_NOTE = " Necessário para a Insígnia da Regra Zero e para vencer na ascensão 8.";

	static final ItemCatalog CATALOG = new ItemCatalog("Equipamentos", false, List.of(
			new ItemSection("cura", "Cura", List.of(
					gear("Bandagem", "imgImage219.webp", "2,5", "Malas", "Remove 30 de ferimento. Leva 4 s para usar."),
					gear("Remédio para fungos", "imgImage226.webp", "2,5", "No chão, nas Raízes",
							"Explode ao atingir uma superfície e cria uma névoa de cura que remove, no total, 45 de ferimento e 27,5 de veneno e de esporos de quem estiver perto."),
					gear("Kit de primeiros socorros", "imgImage222.webp", "5", "Malas",
							"Remove 100 de veneno, de esporos e de ferimento. Leva 5 s para usar."),
					gear("Cura-Tudo", "imgImage245.webp", "5", "Estátua/Bagagem", CURE_ALL),
					gear("Lanterna de fada", "imgImage247.webp", "5", "Estátua/Bagagem", FAERIE_LANTERN))),
			new ItemSection("especiais", "Itens Especiais", List.of(
					gear("Bing Bong", "imgImage235.webp", "5", "Avião",
							"Bicho de pelúcia interagível. Necessário para a Insígnia do Bing Bong."),
					gear("Disco Voador", "imgImage238.webp", "0", "Avião",
							"Disco arremessável que acerta outros escoteiros. Necessário para a Insígnia Ultimate."),
					gear("Zarabatana", "imgImage223.webp", "2,5", "Malas",
							"O dardo remove todos os efeitos, exceto maldição, mas causa 120 de sonolência (ou 150 de calor, se cozido). Não aparece no modo solo."),
					gear("Sinalizador", "imgImage224.webp", "0", "Malas",
							"Solta uma coluna de fumaça da cor do escoteiro. Usado no Pico, chama o helicóptero de resgate."),
					gear("Bússola", "imgImage237.webp", "0", "Avião", "Aponta para o Pico."),
					gear("Bússola pirata", "bussola-pirata.webp", "0", "Malas",
							"Aponta para a bagagem fechada ou a estátua anciã não usada mais próxima."),
					gear("Binóculos", "binoculos.webp", "0", "Avião",
							"Permite enxergar mais longe. Necessário para a Insígnia de Astronomia."),
					gear("Guia", "guia.webp", "0", "Avião", "Ensina a jogar."),
					gear("Pergaminho", "pergaminho.webp", "0", "Fogueiras",
							"Ao ser aberto, mostra uma página rasgada do diário do Chefe Escoteiro."),
					gear("Lanterna", "imgImage239.webp", "5", "Avião",
							"Clareia o ambiente e remove 150 de frio ao longo de 60 s de quem estiver perto. Continua acesa dentro de uma mochila no chão."),
					gear("Castiçal", "castical.webp", "2,5", "Malas",
							"Aceso, impede por 30 s a sonolência da névoa da Melancolia para quem estiver perto. Queima o Fantasma gigante."),
					gear("Tocha", "tocha.webp", "0", "Tumba da Mesa", "Luz temporária."),
					gear("Corneta", "imgImage236.webp", "0", "Avião",
							"Emite uma nota musical. Necessária para a Insígnia da Serenata Animal."),
					gear("Mochila", "imgImage234.webp", "0", "Avião e fogueiras",
							"Ocupa o quarto espaço e permite carregar mais 4 itens."),
					gear("Pochete", "pochete.webp", "0", "Malas",
							"Ocupa o quarto espaço e permite carregar mais 2 itens."))),
			new ItemSection("coletivos", "Itens de uso coletivo", List.of(
					gear("Corda", "imgImage232.webp", "2,5", "Malas",
							"Solta até 10 m de corda escalável a partir do ponto em que foi presa. Cada rolo tem 12,5 m."),
					gear("Fogão Portátil", "imgImage230.webp", "5", "Malas",
							"É posto no chão por 60 s, cozinha alimentos e remove 5 de frio por segundo de quem estiver a até 2 m."),
					gear("Feijão Mágico", "imgImage228.webp", "2,5", "Malas",
							"Depois de 3 s, faz crescer um cipó de até 10 m na direção em que foi posto."),
					gear("Anti-Corda", "Anti-Rope_Spool.webp", "-2,5", "Estátua",
							"Prende na parede e solta até 10 m de corda que levita para o alto. Cada rolo tem 12,5 m."),
					gear("Canhão de Corda", "imgImage231.webp", "2,5", "Malas",
							"Dispara um arpão que prende na parede e libera 7,5 m de corda."),
					gear("Fungo plataforma", "imgImage233.webp", "2,5", "No chão, nas Raízes",
							"Cria uma plataforma de cogumelo ao ser arremessado numa parede."),
					gear("Fungo-nuvem", "fungo-nuvem.webp", "2,5", "No chão, nas Raízes",
							"Cria uma plataforma ao ser arremessado numa parede ou no ponto mais alto de um arremesso no ar."),
					gear("Fungo saltitante", "fungo-saltitante.webp", "2,5", "No chão, nas Raízes",
							"Cria uma plataforma elástica que recupera energia a cada salto. Necessário para a Insígnia de Micoacrobacia."),
					gear("Lançador de Corrente", "imgImage227.webp", "2,5", "Malas",
							"Lança uma corrente escalável entre o ponto onde você está e o ponto mirado. Não dispara para cima."),
					gear("Canhão anti-Corda", "imgImage243.webp", "0", "Estátua",
							"Dispara um gancho que prende na parede e libera uma corda que levita para o alto."),
					gear("Piton", "imgImage229.webp", "2,5", "Malas",
							"É preso na parede e serve de apoio para descansar entre escaladas. Só um escoteiro por vez."),
					gear("Bandeira de checkpoint", "bandeira-de-checkpoint.webp", "2,5", "Malas",
							"Marca um ponto de renascimento de uso único para quem a colocou, guardando os efeitos do momento."),
					gear("Canhão de escoteiro", "canhao-de-escoteiro.webp", "10", "Mesa",
							"Canhão de uso infinito que lança o escoteiro ou objeto colocado dentro dele depois de um pavio curto. Necessário para a Insígnia do Intrépido."),
					gear("Garra de resgate", "garra-de-resgate.webp", "2,5", "Malas",
							"Puxa o escoteiro até o terreno ou puxa outros escoteiros até quem a usa, com alcance de 30 a 40 m. Necessária para a Insígnia de Resposta a Desastres."))),
			new ItemSection("individuais", "Itens de uso individual", List.of(
					gear("Balão", "imgImage251.webp", "-5", "Malas",
							"Reduz a gravidade em 18% por 2 minutos ou até 160 m. Com seis, dá para voar um pouco."),
					gear("Buquê de Balões", "imgImage221.webp", "-15", "Malas",
							"Reduz a gravidade em 54% por buquê. Com dois, dá para voar um pouco."),
					gear("Bolsa Térmica", "imgImage225.webp", "0", "Malas",
							"Remove 360 de frio ao longo de 60 s."),
					gear("Antídoto", "imgImage252.webp", "2,5", "Malas",
							"Remove 35 de veneno, 35 de esporos e 10 de calor."),
					gear("Protetor solar", "protetor-solar.webp", "2,5", "Malas da Mesa",
							"Borrifa uma nuvem que protege do sol por 90 s (3 usos)."),
					gear("Guarda-sol", "guarda-sol.webp", "2,5", "Malas",
							"Protege do sol da Mesa e deixa a queda mais lenta."),
					gear("Fungo de teletransporte", "fungo-de-teletransporte.webp", "2,5", "No chão, nas Raízes",
							"Teletransporta o escoteiro para o lugar onde o fungo cair depois de arremessado."),
					gear("Planador", "planador.webp", "5", "Malas",
							"Permite planar no ar gastando energia. Necessário para a Insígnia de Asa-delta."),
					gear("Jetpack", "jetpack.webp", "0", "Malas",
							"Ocupa o quarto espaço e voa para cima enquanto houver combustível. Itens colocados nele viram combustível."),
					gear("Mochila-foguete", "mochila-foguete.webp", "2,5", "Malas",
							"Ocupa o quarto espaço. Ao acender o pavio, lança quem a usa com pouco controle e explode depois de um voo curto, podendo ferir quem estiver perto."))),
			new ItemSection("miticos", "Itens Místicos", List.of(
					gear("Lancheira de Pandora", "imgImage248.webp", "2,5", "Estátua/Bagagem",
							"Remove todos os efeitos e depois sorteia novos (3 usos)."),
					gear("Efígie de escoteiro", "imgImage249.webp", "5", "Estátua",
							"Revive um escoteiro morto aleatório no lugar em que for posta. Não aparece no modo solo."),
					gear("Cura-Tudo", "imgImage245.webp", "5", "Estátua/Bagagem", CURE_ALL),
					gear("Lanterna de fada", "imgImage247.webp", "5", "Estátua/Bagagem", FAERIE_LANTERN),
					gear("Crânio Amaldiçoado", "imgImage246.webp", "5", "Bagagem anciã",
							"Mata quem o usa e dá aos outros escoteiros 100 de energia extra, curando os efeitos negativos, exceto maldição. Não aparece no modo solo."),
					gear("Ídolo ancião", "idolo-anciao.webp", "40", "Tumba da Mesa",
							"Enquanto está na mão, deixa o escoteiro imune a dano e efeitos negativos. Jogado na lava do Forno ou no caldeirão da Cidadela, concede a Insígnia 24 Quilates."),
					gear("Anti-Zooka", "anti-zooka.webp", "0", "Estátua/Bagagem",
							"Cria uma esfera que inverte a gravidade e enche a energia de quem estiver dentro (3 usos)."),
					gear("Corneta da amizade", "corneta-da-amizade.webp", "2,5", "Estátua/Bagagem",
							"Dá energia infinita por até 7,5 s aos escoteiros por perto. Não aparece no modo solo."),
					gear("Adaga ritual", "adaga-ritual.webp", "2,5", "Estátua/Bagagem",
							"Sacrifica o escoteiro atingido pelas costas e cura quem a usa de todos os efeitos, exceto maldição, dando 100 de energia extra. Não aparece no modo solo."),
					gear("Corneta do Chefe Escoteiro", "corneta-do-chefe-escoteiro.webp", "2,5", "Estátua/Bagagem (raríssima)",
							"Dá 100 de energia extra a quem está por perto, mas invoca o Chefe Escoteiro por 2 minutos, perseguindo quem a tocou."),
					gear("Livro dos Ossos", "livro-dos-ossos.webp", "2,5", "Estátua/Bagagem",
							"Transforma o escoteiro em esqueleto, imune à maioria dos efeitos, mas com ferimentos multiplicados por 8. Usado no esqueleto de um amigo morto, ressuscita-o."),
					gear("Ambição do escoteiro", "ambicao-do-escoteiro.webp", "2,5", "Local secreto",
							"Cria uma aura que dá energia infinita a quem está dentro e causa 30 de petrificação em quem a usa." + SCOUT_RELIC_NOTE),
					gear("Generosidade do escoteiro", "generosidade-do-escoteiro.webp", "2,5", "Local secreto",
							"Duplica o item mirado, causando 25 de petrificação (45 se o item for místico)." + SCOUT_RELIC_NOTE),
					gear("Iniciativa do escoteiro", "iniciativa-do-escoteiro.webp", "2,5", "Local secreto",
							"Ergue quem a usa e os escoteiros próximos e permite andar no vento, causando 7,5 de petrificação." + SCOUT_RELIC_NOTE),
					gear("Tenacidade do escoteiro", "tenacidade-do-escoteiro.webp", "2,5", "Local secreto",
							"Dá 10 s de invencibilidade e cura até 60 de ferimento, esporos, veneno, frio, calor e sonolência, causando de 10 a 30 de petrificação." + SCOUT_RELIC_NOTE),
					gear("Honra do escoteiro", "honra-do-escoteiro.webp", "2,5", "Local secreto",
							"Leva todos os jogadores para o Nadir ao ser ativada." + SCOUT_RELIC_NOTE)))));

	private EquipmentCatalog() {
	}
}
