package ru.funnydwarf.iot.ml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Группа модулей
 */
public class ModuleGroup {

    private final Logger log;

    /**
     * Состояние инициализации групп модулей
     */
    enum State {
        /**
         * Начальное состояние
         */
        NOT_INITIALIZED,
        /**
         * Ошибка инициализации
         */
        INITIALIZATION_ERROR,
        /**
         * Инициализация успешна
         */
        OK
    }

    /**
     * Состояние инициализации группы
     */
    private State state = State.NOT_INITIALIZED;

    /**
     * Имя группы
     */
    private final String name;
    /**
     * Описание группы
     */
    private final String description;

    /**
     * Инициализатор группы модулей
     */
    private final Initializer initializer;

    public ModuleGroup(String name, String description, Initializer initializer) {
        log = LoggerFactory.getLogger(name);
        this.name = name;
        this.description = description;
        this.initializer = initializer;
        initialize();
    }

    /**
     * Выполнить инициализацию
     */
    private void initialize() {
        log.debug("initialize() called");
        try {
            initializer.initialize(this);
            state = State.OK;
        } catch (Exception e) {
            state = State.INITIALIZATION_ERROR;
            log.error(e.getMessage(), e);
        } finally {
            log.info("initialize: {}", state.name());
        }
    }
    
    public State getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


}
