package developersSkills;

import exceptions.MustNotBeNull;

public class DevelopersSkills {
    private long developer_id;
    private long skill_id;

    public long getDeveloper_id() {
        return developer_id;
    }

    public void setDeveloper_id(long developer_id) throws MustNotBeNull {
        if (developer_id > 0) {
            this.developer_id = developer_id;
        } else {
            throw new MustNotBeNull();
        }
    }

    public long getSkill_id() {
        return skill_id;
    }

    public void setSkill_id(long skill_id) throws MustNotBeNull {
        if (skill_id > 0) {
            this.skill_id = skill_id;
        } else {
            throw new MustNotBeNull();
        }
    }

    @Override
    public String toString() {
        return "DevelopersSkills{" +
                "developer_id=" + getDeveloper_id() +
                ", skill_id=" + getSkill_id() +
                '}';
    }
}