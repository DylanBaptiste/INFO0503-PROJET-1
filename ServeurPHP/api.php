<?php
/**
 * Class api
 * Génere les reponses au format JSON encodées apres avoir effectué les actions demandées
 */
class api
{

	//private static hasMakeAResponse = false; //varibale qui pourait permetre de controler si l'api a deja envoyer un reponse pour ne pas que login.php l'utilise plusieur fois si il y a un bug. Utile si il y a JAMAIS de chainage d'action à accomplire

	/**
	 * @param str string la chaine de reussite de l'action à encodé
	 * @return un JSON de succès
	 */
	public function success($str){ return json_encode(array("success" => $str)); }

	/**
	 * @param str string la chaine d'erreur à encodé
	 * @return un JSON de d'erreur
	 */
	public function error($str){ return json_encode(array("error" => $str)); }

	/**
	 * @param $identifiant string identifiant unique de l'utilisateur
	 * @param $mdp string mot de passe de l'utilisateur
	 * @return réponse du serveur d'auth JSON encodé
	 */
	public function login($login = "", $password = ""){
		/*
		if($this->hasMakeAResponse){
			$this->hasMakeAResponse = true;
			return null;
		}
		*/

		if($login == "") 	return $this->error("auncun login donné");
		if($password == "") return $this->error("auncun password donné");

		$url = 'http://localhost:8081/login.html';
		$data = array('login' => $login, 'password' => $password);

		$options = array(
			'http' => array(
				'header'  => "Content-Type: application/json\r\n",
				'method'  => 'POST',
				'content' => json_encode($data)
			)
		);

		$context  = stream_context_create($options);
		$result = file_get_contents($url, false, $context);

		if ($result === FALSE) { 
			return $this->error("Une erreur est survenu lors de la connexion, le PHP n'est pas en cause.");
		}else{
			return $result; //<- result est deja un json généré par je java {success: "login reussi", "id": 666} ou {error: "eugneugneu l'erreur"}
		}

		
	}

	/**
	 * Envoie une requte de creation de compte au serveur d'auth java
	 * @param $login string login unique de l'utilisateur
	 * @param $password string mot de passe de l'utilisateur
	 * @param $confirmPassword string confirmation du mot de passe de l'utilisateur
	 * @return réponse du serveur d'auth JSON encodé
	 */
	public function create($login = "", $password = "", $confirmPassword = ""){

		if($login == "") 			return $this->error("auncun login donné");
		if($password == "") 		return $this->error("auncun password donné");
		if($confirmPassword == "") 	return $this->error("auncun confirmPassword donné");

		if($password != $confirmPassword){
			return $this->error("Le mot de passe et mot de passe de confirmation ne sont pas les mêmes.");
		}

		$url = 'http://localhost:8081/create.html';
		$data = array('login' => $login, 'password' => $password, 'confirmPassword' => $confirmPassword);

		$options = array(
			'http' => array(
				'header'  => "Content-Type: application/json\r\n",
				'method'  => 'POST',
				'content' => json_encode($data)
			)
		);

		$context  = stream_context_create($options);
		$result = file_get_contents($url, false, $context);

		if ($result === FALSE) { 
			return $this->error("Une erreur est survenu lors de la connexion, le PHP n'est pas en cause.");
		}else{
			return $result; //<- result est deja un json généré par je java {success: "login reussi", "id": 666} ou {error: "eugneugneu l'erreur"}
		}

		
	}

	
	public function createSondage($IDClient = "", $Titre = "", $sondageData = null){ 
		if($IDClient == ""){
			return $this->error("Vous n'êtes pas identifié");
		}

		$Titre = trim($Titre);
		if($Titre == ""){
			return $this->error("Choisissez un titre pour le sondage");
		}
		if($sondageData == null){
			return $this->error("Aucun sondage envoyé");
		}

		$sondage = new Sondage($IDClient, $Titre, $sondageData);

		try{
			$formatedTitre = preg_replace('/\s+/',"-",$Titre);
			echo $formatedTitre;
			$file = "sondages/".$IDClient."/".$formatedTitre.".json";
			
			if(!file_exists(dirname($file)))
				mkdir(dirname($file), 0777, true);
			$file = fopen("sondages/".$IDClient."/".$formatedTitre.".json", "w");
			fwrite($file, $sondage->toJSON());
		}
		catch(Exception $e){
			return $this->error("Erreur d'ecriture PHP");
		}
		


		
		
	}

	/**
	 * @param $identifiant string identifiant unique de l'utilisateur
	 * @param $id string identifiant du sondage
	 * @param $rep string indice de la reponse
	 * @return JSON encodé
	 */
	public function voter($identifiant, $id, $rep){ 
		/*
		-on va chercher le fichier de sondage $id
		-on on ajoute $identifiant dans l'indice $rep si il est pas deja present
		*/
		return json_encode(array("error" => "voter pas encore implementé"));
	}
}

class Sondage{
	private $administrateur = "";
	private $titre = "";
	private $sondageData = null;

	function __construct($l, $t, $Q) {
		$this->administrateur = $l;
		$this->titre = $t;
		$this->sondageData = $Q;
	}
	
	public function toJSON(){
		return json_encode(
			array(
				"administrateur" => $this->administrateur,
				"titre" => $this->titre,
				"sondageData" => $this->sondageData
			)
		);
	}

	public function nbQuestions(){
		return count($this->sondageData);
	}

	/*public function nbVoteQuestionByIndex($index){
		return count(array_values($this->sondageData)[$index]);
	}*/
	public function nbVoteReponseByIndex($indexQuestion, $indexReponse){
		$question = array_values($this->sondageData)[$indexQuestion];
		$reponse = array_values($question)[$indexReponse];
		return count($reponse);
	}

}
/*
{
	"administrateur": "toto",
	"titre": "le titre",
	"sondageData": {
		"question1": {
			"reponse1": ["toto1", "toto2"],
			"reponse1": ["toto1", "toto2"],
			"reponse1": ["toto1", "toto2"]
		},
		"question2": {
			"reponse1": ["toto1", "toto2"],
			"reponse1": ["toto1", "toto2"]
		}
	}
}
*/

?>