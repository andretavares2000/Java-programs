package pt.iul.poo.firefight.objects;

public class Score implements Comparable<Score>{
	private String username;
	private int score;
	
	public Score (String username, int score) {
		this.username=username;
		this.score=score;
	}
	
	public int getScore() {
		return score;
	}
	
	public int SetScore(int score1) {
		return this.score = score1;
	}
	public String getUsername() {
		return this.username;
	}
	
	public String SetUsername(String username1) {
		return this.username = username1;
	}
	
	public String toString() {
		return username + " " + score;
	}
	
	

	@Override
	public int compareTo(Score score) {
		
		if (score.getScore() > this.getScore()) {
			return 1;
		}
		else if(score.getScore() < this.getScore()) {
			return -1;
		}
		else {
			return 0;
		}
	}
}
	
	
	
	

