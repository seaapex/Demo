package ca.ljz.demo.ejbs;

import ca.ljz.demo.entities.IGroup;
import ca.ljz.demo.entities.IModel;
import ca.ljz.demo.entities.IUser;

public interface GroupLocal<T extends IModel<U, G>, U extends IUser<U, G>, G extends IGroup<U, G>> extends ILocal<T, U, G> {

}
