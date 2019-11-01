package ezfvnoezfVNCSDQLN;

import java.util.List;

public class pute {

		private String question;
	    private List<User>  reponse;
	    public pute(String question, List<User>  reponse)
	    {
	        this.question = question;
	        this.reponse = reponse;
	    }
		public String getQuestion() {
			return question;
		}
		public void setQuestion(String question) {
			this.question = question;
		}
		public List<User> getReponse() {
			return reponse;
		}
		public void setReponse(List<User> reponse) {
			this.reponse = reponse;
		}

		

	}
