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
	public function login($login = "dqsdf", $password = ""){
		/*
		if($this->hasMakeAResponse){
			$this->hasMakeAResponse = true;
			return null;
		}
		*/

		if($login == "") 	return $this->error("auncun login donné");
		if($password == "") 			return $this->error("auncun password donné");

		$url = 'http://localhost:666';
		$data = array('login' => $login, 'password' => $password);

		$options = array(
			'http' => array(
				'header'  => "Content-type: application/x-www-form-urlencoded\r\n",
				'method'  => 'POST',
				'content' => http_build_query($data)
			)
		);

		$context  = stream_context_create($options);
		$result = file_get_contents($url, false, $context);

		if ($result === FALSE) { 
			return $this->error("Login erreur");
		}else{
			return $this->success("Login reussi");
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