package alumnithon.skilllink.domain.learning.sharedLearning.interfaces;

import alumnithon.skilllink.domain.userprofile.model.User;

public interface OwnableByMentor {
    User getCreatedBy();
}
