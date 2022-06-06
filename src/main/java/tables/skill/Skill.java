package tables.skill;

import exceptions.NumberOfCharactersExceedsTheLimit;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class Skill {
    @Setter
    @Getter
    private long id;
    @Getter
    private String department;
    @Getter
    private String skillLevel;

    public void setDepartment(String department) throws NumberOfCharactersExceedsTheLimit {
        if (department == null) {
            this.department = null;
            return;
        }
        int limit = 50;
        if(limit >= department.length()) {
            this.department = department;
        } else {
            throw new NumberOfCharactersExceedsTheLimit("department", limit, department);
        }
    }

    public void setSkillLevel(String skillLevel) throws NumberOfCharactersExceedsTheLimit {
        if (skillLevel == null) {
            this.skillLevel = null;
            return;
        }
        int limit = 50;
        if(limit >= skillLevel.length()) {
            this.skillLevel = skillLevel;
        } else {
            throw new NumberOfCharactersExceedsTheLimit("skillLevel", limit, skillLevel);
        }
    }
}