package me.yeon.thread.repository;

public interface ParticipantRepository {

	void addParticipant(String sessionCode);
	void removeParticipant(String sessionCode);
	void removeAllParticipants();
	Boolean isParticipant(String sessionCode);

}
