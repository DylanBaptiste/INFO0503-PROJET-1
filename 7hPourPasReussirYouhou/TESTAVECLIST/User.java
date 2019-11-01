package ezfvnoezfVNCSDQLN;

import java.util.HashMap;


public  class User
{	private String question;
    private String reponse;
    public User(String question, String currentReponse)
    {
        this.question = question;
        this.reponse = currentReponse;
    }
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getReponse() {
		return reponse;
	}
	public void setReponse(String reponse) {
		this.reponse = reponse;
	}

	

    }
