package alumnithon.skilllink.domain.learning.shared.interfaces;

import alumnithon.skilllink.domain.userprofile.model.User;

public interface OwnableByMentor {
    User getCreatedBy();
}
