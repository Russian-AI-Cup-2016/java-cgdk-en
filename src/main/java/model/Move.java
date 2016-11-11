package model;

import java.util.Arrays;

/**
 * An encapsulated result of each move of your strategy.
 */
public class Move {
    private double speed;
    private double strafeSpeed;
    private double turn;
    private ActionType action;
    private double castAngle;
    private double minCastDistance;
    private double maxCastDistance = 10000.0D;
    private long statusTargetId = -1L;
    private SkillType skillToLearn;
    private Message[] messages;

    /**
     * @return the current move speed.
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Sets move speed for one tick.
     * <p>
     * By default the speed is in range of {@code -game.wizardBackwardSpeed} to {@code game.wizardForwardSpeed}.
     * These limits can be extended depending on skills of moving wizard and auras of nearby friendly wizards.
     * The {@code HASTENED} status can also greatly speed up a wizard.
     * <p>
     * If a specified value is out of the range, than it become equal to the nearest value of the range.
     * The positive values mean moving forward.
     * <p>
     * If the value {@code hypot(speed / maxSpeed, strafeSpeed / maxStrafeSpeed)} is greater than {@code 1.0}, than both
     * {@code speed} and {@code strafeSpeed} will be divided by this value.
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * @return the current strafe speed.
     */
    public double getStrafeSpeed() {
        return strafeSpeed;
    }

    /**
     * Sets the strafe speed for one tick.
     * <p>
     * By default the strafe speed is in range of {@code -game.wizardStrafeSpeed} to {@code game.wizardStrafeSpeed}.
     * These limits can be extended depending on skills of moving wizard and auras of nearby friendly wizards.
     * The {@code HASTENED} status can also greatly speed up a wizard.
     * <p>
     * If a specified value is out of the range, than it become equal to the nearest value of the range.
     * The positive values mean moving to the right.
     * <p>
     * If the value {@code hypot(speed / maxSpeed, strafeSpeed / maxStrafeSpeed)} is greater than {@code 1.0}, than both
     * {@code speed} and {@code strafeSpeed} will be divided by this value.
     */
    public void setStrafeSpeed(double strafeSpeed) {
        this.strafeSpeed = strafeSpeed;
    }

    /**
     * @return the current turn angle.
     */
    public double getTurn() {
        return turn;
    }

    /**
     * Sets the turn angle for one tick.
     * <p/>
     * The turn angle is in radians and is relative to the current angle of the wizard.
     * By default the turn angle is in range of {@code -game.wizardMaxTurnAngle} to {@code game.wizardMaxTurnAngle}.
     * The {@code HASTENED} status increases bot limits by {@code 1.0 + game.hastenedRotationBonusFactor} times.
     * <p>
     * If a specified value is out of the range, than it become equal to the nearest value of the range.
     * The positive values mean turning clockwise.
     */
    public void setTurn(double turn) {
        this.turn = turn;
    }

    /**
     * @return the current wizard action.
     */
    public ActionType getAction() {
        return action;
    }

    /**
     * Sets the action for one tick.
     * <p>
     * The specified action can be ignored by the game engine, if the controlling wizard has insufficient manapoints or
     * this action is on cooldown.
     */
    public void setAction(ActionType action) {
        this.action = action;
    }

    /**
     * @return the current cast angle.
     */
    public double getCastAngle() {
        return castAngle;
    }

    /**
     * Sets the cast angle for one tick.
     * <p>
     * The cast angle is in radians and is relative to the current angle of the wizard.
     * The cast angle is in range of {@code -game.staffSector / 2.0} to {@code game.staffSector / 2.0}.
     * <p>
     * If a specified value is out of the range, than it become equal to the nearest value of the range.
     * The positive values mean turning clockwise.
     * <p>
     * If the specified action is not a projectile spell, than the game engine will simply ignore this parameter.
     */
    public void setCastAngle(double castAngle) {
        this.castAngle = castAngle;
    }

    /**
     * @return the current minimal cast distance.
     */
    public double getMinCastDistance() {
        return minCastDistance;
    }

    /**
     * Sets the minimal cast distance for one tick.
     * <p>
     * If the distance from the center of the projectile to the point of its occurrence is less than the value of this
     * parameter, the battle properties of the projectile are ignored. The projectile passes freely through all other
     * game objects, except trees.
     * <p>
     * Default value is {@code 0.0}. All collisions between a projectile and its caster are ignored by the game engine.
     * <p>
     * If the specified action is not a projectile spell, than the game engine will simply ignore this parameter.
     */
    public void setMinCastDistance(double minCastDistance) {
        this.minCastDistance = minCastDistance;
    }

    /**
     * @return the current maximal cast distance.
     */
    public double getMaxCastDistance() {
        return maxCastDistance;
    }

    /**
     * Sets the maximal cast distance for one tick.
     * <p>
     * If the distance from the center of the projectile to the point of its occurrence is greater than the value of
     * this parameter, the projectile will be removed from the game world. In this case, the {@code FIREBALL} projectile
     * detonates.
     * <p>
     * The default value of this parameter is higher than the maximal flying range of any projectile in the game.
     * <p>
     * If the specified action is not a projectile spell, than the game engine will simply ignore this parameter.
     */
    public void setMaxCastDistance(double maxCastDistance) {
        this.maxCastDistance = maxCastDistance;
    }

    /**
     * @return the current ID of the status spell target.
     */
    public long getStatusTargetId() {
        return statusTargetId;
    }

    /**
     * Sets the ID of the target living unit to cast a status spell.
     * <p>
     * According to the game rules, the valid targets are only the wizards of the same faction. If the wizard with the
     * specified ID is not found, the status is applied directly to the wizard performing the action. The relative angle
     * to the target should be in range of {@code -game.staffSector / 2.0} to {@code game.staffSector / 2.0}. The
     * distance to the target is limited by {@code wizard.castRange}.
     * <p>
     * The default value of this parameter is {@code -1} (wrong ID).
     * <p>
     * If the specified action is not a status spell, than the game engine will simply ignore this parameter.
     */
    public void setStatusTargetId(long statusTargetId) {
        this.statusTargetId = statusTargetId;
    }

    /**
     * @return the currently selected skill to learn.
     */
    public SkillType getSkillToLearn() {
        return skillToLearn;
    }

    /**
     * Selects the skill to learn before the start of the next tick.
     * <p>
     * The setting will be ignored by the game engine if the current wizard level is less than or equal to the number of
     * the already learned skills. Some skills may also require learning other skills.
     * <p>
     * In some game modes a wizard can not learn skills.
     */
    public void setSkillToLearn(SkillType skillToLearn) {
        this.skillToLearn = skillToLearn;
    }

    /**
     * @return the current messages for friendly wizards.
     */
    public Message[] getMessages() {
        return messages == null ? null : Arrays.copyOf(messages, messages.length);
    }

    /**
     * Sets the messages for the wizards of the same faction.
     * <p>
     * Available only to the master wizard. If not empty, the number of messages must be strictly equal to the number of
     * wizards of the friendly faction (dead or alive) except the master wizard.
     * <p>
     * Messages are addressed in ascending order of wizard IDs. Some messages can be empty ({@code null}), if supported
     * by the programming language used by strategy. In other case all items should be the correct messages.
     * <p>
     * The game engine may ignore the message to a specific wizard, if there is another pending message to the same
     * wizard. If the addressed wizard is dead, then the message will be removed from the game world and the wizard will
     * never get it.
     * <p>
     * Not all game modes support the messages.
     */
    public void setMessages(Message[] messages) {
        this.messages = messages == null ? null : Arrays.copyOf(messages, messages.length);
    }
}
