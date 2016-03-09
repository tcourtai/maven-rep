package org.tcourtai.friends2go.hello;

public enum FlightType {
	DEP_A (Group.A),
	RET_A (Group.A),
	DEP_B (Group.B),
	RET_B (Group.B);
	
	FlightType(Group group) {
        this.group = group;
    }

	private Group group;

	public boolean isInGroup(Group group) {
    return this.group == group;
	}
	
	public Group getGroup() {
		return this.group;
	}

	public enum Group {
    A,
    B;
	}
}