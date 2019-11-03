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
	
	public function createSondage($login = "", $titre = "", $sondageData = null){ 
		
		if($login == ""){ return $this->error("Vous n'êtes pas identifié"); }
		 $titre = trim($titre); if($titre == " ")
		 { return $this->error("Choisissez un titre pour le sondage"); }
		  if($sondageData == null)
		  { return $this->error("Aucun sondage envoyé"); } 
		  $sondage = new Sondage($login, $titre, $sondageData); 
		  if($sondage->isValid() == false){ return $this->error("Le sondage n'est pas valide"); }
		   $ret = $this->writeSondageVote($login, $titre, $sondage); if($ret == true){
				return $this->success("Votre sondage est enregistré"); }
				else{ return $this->error("Creation impossible titre deja existant ?");t; }

}
	public function getSondageFile($login = "", $titre = ""){ 
		
		if($login == ""){
			return $this->error("Choisisser un admin");
		}
		if($this->folder_exist("sondages/".$login) == false){
			return $this->error("Cette administrateur n'existe pas");
		}
		$titre = trim($titre);
		$formatedtitre = preg_replace('/\s+/',"-",$titre);
		if($formatedtitre == ""){
			return $this->error("Choisissez un titre pour le sondage");
		}
		$filePath = "sondages/".$login."/".$formatedtitre.".json";
		if(file_exists($filePath) === false){
			return $this->error("Ce sondage existe pas");
		}

		$file = @fopen($filePath, "r");
		if($file === false){
			return $this->error("Ce sondage existe pas");
		}
		return fread($file, filesize($filePath));
	}

	
	private function writeSondageVote($login, $titre, $sondage){
		try{
			$formatedtitre = preg_replace('/\s+/',"-",$titre);
			$file = "sondages/".$login."/".$formatedtitre.".json";
			
			if(!file_exists(dirname($file)))
				mkdir(dirname($file), 0777, true);
			$file = fopen("sondages/".$login."/".$formatedtitre.".json", "w");
			fwrite($file, $sondage->toJSON());
			return true;
		}
		catch(Exception $e){
			return $this->error("Erreur d'ecriture PHP");
		}
	}
	public function voter($administrateur= "", $titre = "", $sondageData = "")
	{ 
		if($this->folder_exist("sondages/".$administrateur) == false){
			 return $this->error("Cette administrateur n'existe pas"); 
			} 
	$titre = trim($titre); if($titre == " ")
	{ return $this->error("ntm fdp donne moi un titre correcte");
	} 
	$formatedtitre = preg_replace('/\s+/',"-",$titre); 
	$filePath = "sondages/".$administrateur."/".$formatedtitre.".json"; 
	if( !(file_exists($filePath)) ){ 
		return $this->error("Ce sondage n'existe pas"); 
	} if($sondageData == "")
	{ return $this->error("Aucun vote(s) envoyé"); }
	 $sondage = new Sondage($administrateur, $titre, $sondageData); 
	 if($sondage->isValid() == false){
		  return $this->error("Le sondage n'est pas valide"); 
		}
	  $ret = $this->writeSondageVote($administrateur, $titre, $sondage); 
	  if($ret == true){ return $this->success("Votes enregistrés"); }
	  else{ return $ret; }
	}
	public function getSondage($admin = ""){
		if($admin == ""){
			return $this->error("Aucun nom d'amin envoyé");
		}
		if($this->folder_exist("sondages/".$admin) == false){
			return $this->error("Cette administrateur n'a pas de sondage");
		}
		$files = scandir("sondages/".$admin);
		
		$delete_extention = function($value) { return basename($value, '.json'); };
		return json_encode(array(
			"success" => "Voici le(s) sondage(s) de ".$admin.": ",
			"sondages" => array_map($delete_extention, array_slice($files, 2))
			)
			
		);
	}
	public function getAdmin($admin = ""){
		if($admin == ""){
			return $this->error("Aucun nom d'amin envoyé");
		}
		if($this->folder_exist("sondages/".$admin) == false){
			return $this->error("Cette administrateur n'a pas de sondage");
		}
		$files = scandir("sondages/".$admin);
		
		$delete_extention = function($value) { return basename($value, '.json'); };
		return json_encode(array(
			"success" => "Voici les utilisateurs ayant un sondage ",
			"sondages" => array_map($delete_extention, array_slice($files, 2))
			)
			
		);
	}
	public function folder_exist($folder)
	{
		$path = realpath($folder);
		return ($path !== false && is_dir($path)) ? $path : false;
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
	public function isValid(){
		return is_array($this->sondageData);
	}
	private function getListeVotantByIndex($i,$j){
		
		if(!isset($this->sondageData[$i])) return false;
		$question = $this->sondageData[$i];
		$nameQuestion = array_keys($question)[0];
		
		if(!isset($question[$nameQuestion])) return false;
		$reponses = $question[$nameQuestion];
		if(!isset(array_keys($reponses)[$j])) return false;
		$nameReponse = array_keys($reponses)[$j];
		if(!isset($reponses[$nameReponse])) return false;
		$ret = $reponses[$nameReponse];
		return $ret;
	}


	
	private function updateListe($i, $j, $liste){
		
		if(!isset($this->sondageData[$i])) return false;
		$question = $this->sondageData[$i];
		$nameQuestion = array_keys($question)[0];
		
		if(!isset($question[$nameQuestion])) return false;
		$reponses = $question[$nameQuestion];
		if(!isset(array_keys($reponses)[$j])) return false;
		$nameReponse = array_keys($reponses)[$j];
		if(!isset($reponses[$nameReponse])) return false;
		$ret = $reponses[$nameReponse];
		return $ret;
	}
	public function voter($login, $q, $r){
		echo "</br></br>";
		$liste = $this->getListeVotantByIndex(1,1);
		if($liste != false) print_r($liste);
		
		echo "</br></br>";
		/*if( is_array(array_values($this->sondageData) && is_array( array_values(array_values($this->sondageData[$q])[0]) )  && is_array(array_values(array_values($this->sondageData[$q])[0])[$r]))){
			return false;
		}
		if(in_array($login, array_values(array_values($this->sondageData[$q])[0])[$r])){
			//print_r(array_values(array_values($this->sondageData[$q])[0])[$r]);
			return "Vous avez deja voté ici";
		}
		else{
			//print_r(array_values(array_values($this->sondageData[$q])[0])[$r]);
			array_push(array_values(array_values($this->sondageData[$q])[0])[$r], $login);
			return true;
		}*/
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
