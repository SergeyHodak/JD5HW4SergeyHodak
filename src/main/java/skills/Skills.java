package skills;

import exceptions.NumberOfCharactersExceedsTheLimit;

public class Skills {
    private long id;
    private String department;
    private String  skill_level;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) throws NumberOfCharactersExceedsTheLimit {
        int limit = 50;
        if(limit >= department.length()) {
            this.department = department;
        } else {
            throw new NumberOfCharactersExceedsTheLimit("department", limit, department);
        }
    }

    public String getSkill_level() {
        return skill_level;
    }

    public void setSkill_level(String skill_level) throws NumberOfCharactersExceedsTheLimit {
        int limit = 50;
        if(limit >= skill_level.length()) {
            this.skill_level = skill_level;
        } else {
            throw new NumberOfCharactersExceedsTheLimit("name", limit, skill_level);
        }
    }

    @Override
    public String toString() {
        return "Skills{" +
                "id=" + getId() +
                ", department='" + getDepartment() + '\'' +
                ", skill_level='" + getSkill_level() + '\'' +
                '}';
    }
}