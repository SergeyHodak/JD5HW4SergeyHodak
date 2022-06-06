package tables.developer_skill;

import exceptions.MustNotBeNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public class DeveloperSkill {
    @Getter
    private long developerId;
    @Getter
    private long skillId;

    public void setDeveloperId(long developerId) throws MustNotBeNull {
        if (developerId > 0) {
            this.developerId = developerId;
        } else {
            throw new MustNotBeNull();
        }
    }

    public void setSkillId(long skillId) throws MustNotBeNull {
        if (skillId > 0) {
            this.skillId = skillId;
        } else {
            throw new MustNotBeNull();
        }
    }
}