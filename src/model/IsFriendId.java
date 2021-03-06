package model;

// Generated 23.04.2014 16:22:23 by Hibernate Tools 3.4.0.CR1

/**
 * IsFriendId generated by hbm2java
 */
public class IsFriendId implements java.io.Serializable {

	private int userId1;
	private int userId2;

	public IsFriendId() {
	}

	public IsFriendId(int userId1, int userId2) {
		this.userId1 = userId1;
		this.userId2 = userId2;
	}

	public int getUserId1() {
		return this.userId1;
	}

	public void setUserId1(int userId1) {
		this.userId1 = userId1;
	}

	public int getUserId2() {
		return this.userId2;
	}

	public void setUserId2(int userId2) {
		this.userId2 = userId2;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof IsFriendId))
			return false;
		IsFriendId castOther = (IsFriendId) other;

		return (this.getUserId1() == castOther.getUserId1())
				&& (this.getUserId2() == castOther.getUserId2());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getUserId1();
		result = 37 * result + this.getUserId2();
		return result;
	}

}
