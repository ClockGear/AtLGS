package service;

import domain.GameEvent;
import jpa.JPA_GameEvent;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by Dennis van Opstal on 17-11-2017.
 */
@Dependent
public class GameEventService implements IService<GameEvent>{
    @Inject
    private JPA_GameEvent jpaGameEvent;

    public void create(GameEvent entity) {
        jpaGameEvent.create(entity);
    }

    public void edit(GameEvent entity) {
        jpaGameEvent.edit(entity);
    }

    public void remove(long id) {
        jpaGameEvent.remove(id);
    }

    public GameEvent find(long id) {
        return jpaGameEvent.find(id);
    }

    public List<GameEvent> getAll() {
        return jpaGameEvent.getAll();
    }
}
