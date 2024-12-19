package botexecution.handlers.dndhandlers;

import botexecution.handlers.SiteParseHandler;
import botexecution.handlers.corehandlers.DataHandler;
import botexecution.handlers.corehandlers.TextHandler;
import botexecution.mainobjects.ChatSession;
import botexecution.commands.CurrentProcess;
import common.Constants;
import dnd.characteristics.AbilityDnD;
import dnd.characteristics.FeatDnD;
import dnd.characteristics.SpellDnD;
import dnd.equipment.*;
import dnd.equipment.InstrumentDnD;
import dnd.mainobjects.PlayerDnD;
import dnd.values.aspectvalues.ItemsIdsDnD;
import dnd.values.characteristicsvalues.JobsDnD;
import dnd.values.masteryvalues.DamageTypeDnD;
import dnd.values.masteryvalues.MasteryTypeDnD;
import dnd.values.aspectvalues.*;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.telegram.telegrambots.abilitybots.api.objects.MessageContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

public class DnDItemHandler {
    private final DataHandler knowledge;
    private final TextHandler walkieTalkie;
    private final SiteParseHandler archive;
    private final DnDNotificationHandler secretMessages;

    public ChatSession getCampaignSession(ChatSession currentUser) {
        if (currentUser.currentCampaign == null) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NULL);
            return null;
        }
        ChatSession currentCampaign = knowledge.getSession(currentUser.currentCampaign.toString());
        if (currentCampaign == null) {
            walkieTalkie.patternExecute(currentUser, Constants.SET_CAMPAIGN_NULL);
            return null;
        }
        return currentCampaign;
    }

    public void addAspect(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentUser.currentContext != CurrentProcess.FREE) {
            walkieTalkie.patternExecute(currentUser, Constants.CURRENT_COMMAND_RESTRICT);
            return;
        }
        else if (currentCampaign == null || secretMessages.isNotLegal(ctx, "par_item1-dm")) {
            return;
        }

        currentUser.lastParameter = ctx.firstArg().toLowerCase();
        String searchLine = ctx.secondArg();

        ArrayList<String> foundAspects = searchAspects(searchLine, currentUser.lastParameter);

        currentUser.currentContext = CurrentProcess.ADDING_AN_ASPECT_DND;
        if (foundAspects.size() > 1) {
            walkieTalkie.articleMessaging(aspectSearchWriter(foundAspects), currentUser, null);
        } else if (foundAspects.isEmpty()) {
            currentUser.currentContext = CurrentProcess.FREE;
            walkieTalkie.patternExecute(currentUser, "Ваш предмет не был найден.\n" +
                    "Вы можете создать свой предмет, введя 'Новый предмет' (это пример, для значений смотрите /help).",
                    null, false);
        } else {
            addAspectSecondStage(currentUser, searchLine);
        }

        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(currentUser);
    }

    public void addAspectSecondStage(ChatSession cs, String searchLine) {
        ChatSession currentCampaign = getCampaignSession(cs);
        if (currentCampaign == null) {
            return;
        }

        StringBuilder additionMessage = new StringBuilder();

        switch (cs.lastParameter) {
            case "-i" -> {
                ItemDnD item = itemAllocator.get(searchAspect(searchLine, cs.lastParameter));
                additionMessage.append("В ваш набор предметов добавлено: ").append(item.name);
                currentCampaign.activeDm.itemCollection.add(item);
                currentCampaign.activeDm.itemCollectionNames.add(item.name);
            }
            case "-w" -> {
                WeaponDnD weapon = weaponAllocator.get(searchAspect(searchLine, cs.lastParameter));
                additionMessage.append("В ваш набор оружия добавлено: ").append(weapon.name);
                currentCampaign.activeDm.weaponCollection.add(weapon);
                currentCampaign.activeDm.weaponCollectionNames.add(weapon.name);
            }
            case "-a" -> {
                ArmorDnD armor = armorAllocator.get(searchAspect(searchLine, cs.lastParameter));
                additionMessage.append("В ваш набор брони добавлено: ").append(armor.name);
                currentCampaign.activeDm.armorCollection.add(armor);
                currentCampaign.activeDm.armorCollectionNames.add(armor.name);
            }
            case "-in" -> {
                InstrumentDnD instruments = instrumentsAllocator.get(searchAspect(searchLine, cs.lastParameter));
                additionMessage.append("В ваш набор инструментов добавлено: ").append(instruments.name);
                currentCampaign.activeDm.instrumentsCollection.add(instruments);
                currentCampaign.activeDm.instrumentsCollectionNames.add(instruments.name);
            }
            case "-k" -> {
                KitDnD kit = kitAllocator.get(searchAspect(searchLine, cs.lastParameter));
                additionMessage.append("В ваш набор комплектов добавлено: ").append(kit.name);
                currentCampaign.activeDm.kitCollection.add(kit);
                currentCampaign.activeDm.kitCollectionNames.add(kit.name);
            }
            case "-f" -> {
                FeatDnD feat = featAllocator.get(searchAspect(searchLine, cs.lastParameter));
                additionMessage.append("В ваш набор черт добавлено: ").append(feat.title);
                currentCampaign.activeDm.featCollection.add(feat);
                currentCampaign.activeDm.featCollectionNames.add(feat.title);
            }
            case "-ab" -> {
                AbilityDnD ability = abilityAllocator.get(searchAspect(searchLine, cs.lastParameter));
                additionMessage.append("В ваш набор способностей добавлено: ").append(ability.title);
                currentCampaign.activeDm.abilityCollection.add(ability);
                currentCampaign.activeDm.abilityCollectionNames.add(ability.title);
            }
            case "-sp" -> {
                SpellDnD spell = spellAllocator.get(searchAspect(searchLine, cs.lastParameter));
                additionMessage.append("В ваш набор заклинаний добавлено: ").append(spell.title);
                currentCampaign.activeDm.spellCollection.add(spell);
                currentCampaign.activeDm.spellCollectionNames.add(spell.title);
            }
            default -> {
                walkieTalkie.patternExecute(cs, "Случилась непредвиденная ошибка. Повторите ещё раз.");
                knowledge.renewListChat(currentCampaign);
                knowledge.renewListChat(cs);
                return;
            }
        }

        additionMessage.append("\n").append("Вы можете изменить описание предмета, выбрав его командой /editanitem.")
                .append("\n").append("Перед этом его нужно установить через /setanitem.");
        walkieTalkie.patternExecute(cs, additionMessage.toString());
        cs.currentContext = CurrentProcess.FREE;
        cs.lastParameter = "";
        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(cs);
    }

    public void showAspect(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null || secretMessages.isNotLegal(ctx, "dm")) {
            return;
        }

        String aspect;
        switch (ctx.firstArg()) {
            case "-i" -> aspect = aspectStorageWriter(currentCampaign.activeDm.itemCollectionNames);
            case "-w" -> aspect = aspectStorageWriter(currentCampaign.activeDm.weaponCollectionNames);
            case "-a" -> aspect = aspectStorageWriter(currentCampaign.activeDm.armorCollectionNames);
            case "-in" -> aspect = aspectStorageWriter(currentCampaign.activeDm.instrumentsCollectionNames);
            case "-k" -> aspect = aspectStorageWriter(currentCampaign.activeDm.kitCollectionNames);
            case "-f" -> aspect = aspectStorageWriter(currentCampaign.activeDm.featCollectionNames);
            case "-ab" -> aspect = aspectStorageWriter(currentCampaign.activeDm.abilityCollectionNames);
            case "-sp" -> aspect = aspectStorageWriter(currentCampaign.activeDm.spellCollectionNames);
            default -> {
                walkieTalkie.patternExecute(currentUser, "Введите корректный параметр.");
                knowledge.renewListChat(currentCampaign);
                knowledge.renewListChat(currentUser);
                return;
            }
        }

        walkieTalkie.articleMessaging(aspect, currentUser, null);
        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(currentUser);
    }

    public void setAspect(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null || secretMessages.isNotLegal(ctx, "par_item1-int2-dm")) {
            return;
        }

        int index = Integer.parseInt(ctx.secondArg()) - 1;

        switch (ctx.firstArg()) {
            case "-i" -> {
                if (index < 0 || index >= currentCampaign.activeDm.itemCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }
                currentCampaign.activeDm.editItem = currentCampaign.activeDm.itemCollection.get(index);
                currentCampaign.activeDm.currentAspectType = AspectCategoriesDnD.ITEM;
                walkieTalkie.patternExecute(currentUser,
                        "Предмет " + currentCampaign.activeDm.editItem.name + " был установлен.");
            }
            case "-w" -> {
                if (index < 0 || index >= currentCampaign.activeDm.weaponCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }
                currentCampaign.activeDm.editWeapon = currentCampaign.activeDm.weaponCollection.get(index);
                currentCampaign.activeDm.currentAspectType = AspectCategoriesDnD.WEAPON;
                walkieTalkie.patternExecute(currentUser,
                        "Оружие " + currentCampaign.activeDm.editWeapon.name + " было установлено.");
            }
            case "-a" -> {
                if (index < 0 || index >= currentCampaign.activeDm.armorCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }
                currentCampaign.activeDm.editArmor = currentCampaign.activeDm.armorCollection.get(index);
                currentCampaign.activeDm.currentAspectType = AspectCategoriesDnD.ARMOR;
                walkieTalkie.patternExecute(currentUser,
                        "Броня " + currentCampaign.activeDm.editArmor.name + " была установлена.");
            }
            case "-in" -> {
                if (index < 0 || index >= currentCampaign.activeDm.instrumentsCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }
                currentCampaign.activeDm.editInstruments = currentCampaign.activeDm.instrumentsCollection.get(index);
                currentCampaign.activeDm.currentAspectType = AspectCategoriesDnD.INSTRUMENTS;
                walkieTalkie.patternExecute(currentUser,
                        "Инструменты " + currentCampaign.activeDm.editInstruments.name + " были установлены.");
            }
            case "-k" -> {
                if (index < 0 || index >= currentCampaign.activeDm.kitCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }
                currentCampaign.activeDm.editKit = currentCampaign.activeDm.kitCollection.get(index);
                currentCampaign.activeDm.currentAspectType = AspectCategoriesDnD.KIT;
                walkieTalkie.patternExecute(currentUser,
                        "Комплект " + currentCampaign.activeDm.editKit.name + " был установлен.");
            }
            case "-f" -> {
                if (index < 0 || index >= currentCampaign.activeDm.featCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }
                currentCampaign.activeDm.editFeat = currentCampaign.activeDm.featCollection.get(index);
                currentCampaign.activeDm.currentAspectType = AspectCategoriesDnD.FEAT;
                walkieTalkie.patternExecute(currentUser,
                        "Черта " + currentCampaign.activeDm.editFeat.title + " была установлена.");
            }
            case "-ab" -> {
                if (index < 0 || index >= currentCampaign.activeDm.abilityCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }
                currentCampaign.activeDm.editAbility = currentCampaign.activeDm.abilityCollection.get(index);
                currentCampaign.activeDm.currentAspectType = AspectCategoriesDnD.ABILITY;
                walkieTalkie.patternExecute(currentUser,
                        "Способность " + currentCampaign.activeDm.editAbility.title + " была установлена.");
            }
            case "-sp" -> {
                if (index < 0 || index >= currentCampaign.activeDm.spellCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }
                currentCampaign.activeDm.editSpell = currentCampaign.activeDm.spellCollection.get(index);
                currentCampaign.activeDm.currentAspectType = AspectCategoriesDnD.SPELL;
                walkieTalkie.patternExecute(currentUser,
                        "Заклинание " + currentCampaign.activeDm.editSpell.title + " было установлено.");
            }
            default -> {
                walkieTalkie.patternExecute(currentUser, "Введите корректный параметр.");
                knowledge.renewListChat(currentCampaign);
                knowledge.renewListChat(currentUser);
                return;
            }
        }

        currentCampaign.activeDm.editIndex = index;

        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(currentUser);
    }

    public void seeCurrentAspect(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null || secretMessages.isNotLegal(ctx, "dm")) {
            return;
        }

        StringBuilder aspectStatus = new StringBuilder();
        aspectStatus.append("На данный момент основным предметом у вас стоит: \n");
        switch (currentCampaign.activeDm.currentAspectType) {
            case ITEM -> aspectStatus.append(currentCampaign.activeDm.editItem.toString());
            case WEAPON -> aspectStatus.append(currentCampaign.activeDm.editWeapon.toString());
            case ARMOR -> aspectStatus.append(currentCampaign.activeDm.editArmor.toString());
            case INSTRUMENTS -> {
                aspectStatus.append(currentCampaign.activeDm.editInstruments.toString());
                aspectStatus.append(archive.toolGrabber(currentCampaign.activeDm.editInstruments.id));
            }
            case KIT -> aspectStatus.append(currentCampaign.activeDm.editKit.toString());
            case FEAT -> aspectStatus.append(currentCampaign.activeDm.editFeat.toString());
            case ABILITY -> aspectStatus.append(currentCampaign.activeDm.editAbility.toString());
            case SPELL -> aspectStatus.append(currentCampaign.activeDm.editSpell.toString());
            default -> {
                aspectStatus.append("Ничего.");
                walkieTalkie.patternExecute(currentUser, aspectStatus.toString());
                knowledge.renewListChat(currentCampaign);
                knowledge.renewListChat(currentUser);
                return;
            }
        }
        aspectStatus.append("(Индекс предмета: ").append(currentCampaign.activeDm.currentAspectType.toString())
                    .append(" - ").append(currentCampaign.activeDm.editIndex + 1).append(")");

        walkieTalkie.patternExecute(currentUser, aspectStatus.toString());
        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(currentUser);
    }

    public void editAspect(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentUser.currentContext != CurrentProcess.FREE) {
            walkieTalkie.patternExecute(currentUser, Constants.CURRENT_COMMAND_RESTRICT);
            return;
        }
        else if (currentCampaign == null || secretMessages.isNotLegal(ctx, "dm")) {
            return;
        }

        currentUser.currentContext = CurrentProcess.EDITING_AN_ASPECT_DND;
        currentCampaign.activeDm.editParameter = ctx.firstArg();
        walkieTalkie.patternExecute(currentUser, "Введите изменения.");
        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(currentUser);
    }

    public void editAspectSecondStage(ChatSession cs, String response) {
        ChatSession currentCampaign = getCampaignSession(cs);
        if (currentCampaign == null) {
            return;
        }

        if (Objects.equals(currentCampaign.activeDm.editParameter, "-c")) {
            if (!CurrencyDnD.getCurrencies().containsKey(response)) {
                walkieTalkie.patternExecute(cs,
                        "Введите корректную валюту (Медь, Серебро, Золото).");
                return;
            }
        }

        if (currentCampaign.activeDm.editParameter.equals("-t")) {
            if (!ItemsIdsDnD.getTypes().containsKey(response)) {
                walkieTalkie.patternExecute(cs,
                        "Введите корректный тип оружия (Простое, Боевое)");
                return;
            }
        }

        if (currentCampaign.activeDm.editParameter.equals("-r")) {
            if (!ItemsIdsDnD.getRanges().containsKey(response)) {
                walkieTalkie.patternExecute(cs,
                        "Введите корректную специальность оружия (Ближнего боя, Дальнего боя)");
                return;
            }
        }

        if (currentCampaign.activeDm.editParameter.equals("-dt")) {
            if (!DamageTypeDnD.getTypes().containsKey(response)) {
                walkieTalkie.patternExecute(cs,
                        "Введите корректный тип урона (Нет, Рубящий, Колющий, " +
                                "Дробящий, Яд, Кислота, Огонь, Холод, Святой, Некротика, " +
                                "Молния, Громовой, Силовой, Психический)");
                return;
            }
        }

        if (currentCampaign.activeDm.editParameter.equals("-at")) {
            if (!ItemsIdsDnD.getArmorTypes().containsKey(response)) {
                walkieTalkie.patternExecute(cs,
                        "Введите корректный тип брони (Легкая, Средняя, Тяжелая, Щит)");
                return;
            }
        }

        if (currentCampaign.activeDm.editParameter.contains("-j")) {
            if (!JobsDnD.getJobs().containsKey(response)) {
                walkieTalkie.patternExecute(cs,
                        "Введите корректный класс (Изобретатель, Варвар, Бард, Жрец, Друид, Воин, " +
                                "Монах, Паладин, Следопыт, Плут, Чародей, Колдун, Волшебник, Свой класс)");
                return;
            }
        }

        if (currentCampaign.activeDm.editParameter.contains("-cm")) {
            if (!SpellComponentsDnD.getSpellComp().containsKey(response)) {
                walkieTalkie.patternExecute(cs,
                        "Введите корректный компонент заклинания (Вербальный, Соматический, Материальный)");
                return;
            }
        }

        if (currentCampaign.activeDm.editParameter.contains("-tr")) {
            if (!WeaponTraitsDnD.getTraits().containsKey(response)) {
                walkieTalkie.patternExecute(cs,
                        "Введите корректное качество (Аммуниция, Двуручное, Длинное, Дистанция, Легкое, " +
                                "Бросаемое, Специальное, Перезарядка, Тяжелое, Универсальное, Фехтовальное)");
                return;
            }
        }

        if (currentCampaign.activeDm.editParameter.contains("-adv")) {
            if (!MasteryTypeDnD.getAdvantages().containsKey(response)) {
                walkieTalkie.patternExecute(cs,
                        "Введите корректное преимущество (Сила, Ловкость, Выносливость, " +
                                "Интеллект, Мудрость, Харизма, " +
                                "Акробатика, Анализ, Атлетика, Восприятие, Выживание, Выступление, Запугивание, " +
                                "История, Ловкость рук, Магия, Медицина, Обман, Природа, Проницательность, " +
                                "Религия, Скрытность, Убеждение, Уход за животными)");
                return;
            }
        }

        if (currentCampaign.activeDm.editParameter.equals("d")) {
            try {
                double num = Double.parseDouble(response);
            } catch (NumberFormatException e) {
                walkieTalkie.patternExecute(cs, "Введите число.");
                return;
            }
        }

        if (("avw".contains(currentCampaign.activeDm.editParameter)
                && currentCampaign.activeDm.editParameter.length() == 1)
                || currentCampaign.activeDm.editParameter.equals("-mind")
                || currentCampaign.activeDm.editParameter.equals("-maxd")
                || currentCampaign.activeDm.editParameter.equals("-hb")
                || currentCampaign.activeDm.editParameter.equals("-ac")
                || currentCampaign.activeDm.editParameter.equals("-dmm")
                || currentCampaign.activeDm.editParameter.equals("-sr")
                || currentCampaign.activeDm.editParameter.equals("-l")) {
            try {
                int num = Integer.parseInt(response);
            } catch (NumberFormatException e) {
                walkieTalkie.patternExecute(cs, "Введите целое число.");
                return;
            }
        }

        switch (currentCampaign.activeDm.currentAspectType) {
            case ITEM -> {
                switch (currentCampaign.activeDm.editParameter) {
                    case "-n" -> currentCampaign.activeDm.editItem.setName(response);
                    case "-s" -> currentCampaign.activeDm.editItem.setSummary(response);
                    case "-a" -> currentCampaign.activeDm.editItem.setAmountInInstance(Integer.parseInt(response));
                    case "-v" -> currentCampaign.activeDm.editItem.setValue(Integer.parseInt(response));
                    case "-c" -> currentCampaign.activeDm.editItem.setCurrencyGrade(CurrencyDnD.getCurrency(response));
                    case "-w" ->  currentCampaign.activeDm.editItem.setWeight(Integer.parseInt(response));
                    case "-e" -> currentCampaign.activeDm.editItem.setEffects(response);
                    default -> {
                        walkieTalkie.patternExecute(cs, "Введите корректный параметр.");
                        cs.currentContext = CurrentProcess.FREE;
                        currentCampaign.activeDm.editParameter = "";
                        knowledge.renewListChat(cs);
                        knowledge.renewListChat(currentCampaign);
                        return;
                    }
                }

                currentCampaign.activeDm.itemCollection.set(currentCampaign.activeDm.editIndex,
                        currentCampaign.activeDm.editItem);
            }
            case WEAPON -> {
                switch (currentCampaign.activeDm.editParameter) {
                    case "-n" -> currentCampaign.activeDm.editWeapon.setName(response);
                    case "-s" -> currentCampaign.activeDm.editWeapon.setSummary(response);
                    case "-a" -> currentCampaign.activeDm.editWeapon.setAmountInInstance(Integer.parseInt(response));
                    case "-v" -> currentCampaign.activeDm.editWeapon.setValue(Integer.parseInt(response));
                    case "-c" -> currentCampaign.activeDm.editWeapon.setCurrencyGrade(CurrencyDnD.getCurrency(response));
                    case "-w" ->  currentCampaign.activeDm.editWeapon.setWeight(Integer.parseInt(response));
                    case "-t" -> currentCampaign.activeDm.editWeapon.setType(ItemsIdsDnD.getType(response));
                    case "-r" -> currentCampaign.activeDm.editWeapon.setRange(ItemsIdsDnD.getRange(response));
                    case "-ad" -> currentCampaign.activeDm.editWeapon.setAttackDice(response);
                    case "-dt" -> currentCampaign.activeDm.editWeapon.setDamageType(response);
                    case "-atr" -> currentCampaign.activeDm.editWeapon.addTraits(WeaponTraitsDnD.getTrait(response));
                    case "-dtr" -> currentCampaign.activeDm.editWeapon.delTraits(WeaponTraitsDnD.getTrait(response));
                    case "-mind" -> currentCampaign.activeDm.editWeapon.setMinDistance(Integer.parseInt(response));
                    case "-maxd" -> currentCampaign.activeDm.editWeapon.setMaxDistance(Integer.parseInt(response));
                    case "-e" -> currentCampaign.activeDm.editWeapon.setEffects(response);
                    case "-hb" -> currentCampaign.activeDm.editWeapon.setHitBonus(Integer.parseInt(response));
                    default -> {
                        walkieTalkie.patternExecute(cs, "Введите корректный параметр.");
                        cs.currentContext = CurrentProcess.FREE;
                        currentCampaign.activeDm.editParameter = "";
                        knowledge.renewListChat(cs);
                        knowledge.renewListChat(currentCampaign);
                        return;
                    }
                }

                currentCampaign.activeDm.weaponCollection.set(currentCampaign.activeDm.editIndex,
                        currentCampaign.activeDm.editWeapon);
            }
            case ARMOR -> {
                switch (currentCampaign.activeDm.editParameter) {
                    case "-n" -> currentCampaign.activeDm.editArmor.setName(response);
                    case "-s" -> currentCampaign.activeDm.editArmor.setSummary(response);
                    case "-a" -> currentCampaign.activeDm.editArmor.setAmountInInstance(Integer.parseInt(response));
                    case "-v" -> currentCampaign.activeDm.editArmor.setValue(Integer.parseInt(response));
                    case "-c" -> currentCampaign.activeDm.editArmor.setCurrencyGrade(CurrencyDnD.getCurrency(response));
                    case "-w" ->  currentCampaign.activeDm.editArmor.setWeight(Integer.parseInt(response));
                    case "-at" -> currentCampaign.activeDm.editArmor.setType(ItemsIdsDnD.getArmorType(response));
                    case "-ac" -> currentCampaign.activeDm.editArmor.setArmorClass(Integer.parseInt(response));
                    case "-dmm" -> currentCampaign.activeDm.editArmor.setDexterityModMax(Integer.parseInt(response));
                    case "-sr" -> currentCampaign.activeDm.editArmor.setStrengthRequirement(Integer.parseInt(response));
                    case "-hsd" -> currentCampaign.activeDm.editArmor.changeHasStealthDisadvantage();
                    case "-e" -> currentCampaign.activeDm.editArmor.setEffects(response);
                    default -> {
                        walkieTalkie.patternExecute(cs, "Введите корректный параметр.");
                        cs.currentContext = CurrentProcess.FREE;
                        currentCampaign.activeDm.editParameter = "";
                        knowledge.renewListChat(cs);
                        knowledge.renewListChat(currentCampaign);
                        return;
                    }
                }

                currentCampaign.activeDm.armorCollection.set(currentCampaign.activeDm.editIndex,
                        currentCampaign.activeDm.editArmor);
            }
            case INSTRUMENTS -> {
                switch (currentCampaign.activeDm.editParameter) {
                    case "-n" -> currentCampaign.activeDm.editInstruments.setName(response);
                    case "-s" -> currentCampaign.activeDm.editInstruments.setSummary(response);
                    case "-a" -> currentCampaign.activeDm.editInstruments.setAmountInInstance(Integer.parseInt(response));
                    case "-v" -> currentCampaign.activeDm.editInstruments.setValue(Integer.parseInt(response));
                    case "-c" -> currentCampaign.activeDm.editInstruments.setCurrencyGrade(CurrencyDnD.getCurrency(response));
                    case "-w" ->  currentCampaign.activeDm.editInstruments.setWeight(Integer.parseInt(response));
                    //case "-aadv" -> currentCampaign.activeDm.editInstruments.addAdvantage(MasteryTypeDnD.getAdvantage(response));
                    //case "-dadv" -> currentCampaign.activeDm.editInstruments.delAdvantage(MasteryTypeDnD.getAdvantage(response));
                    default -> {
                        walkieTalkie.patternExecute(cs, "Введите корректный параметр.");
                        cs.currentContext = CurrentProcess.FREE;
                        currentCampaign.activeDm.editParameter = "";
                        knowledge.renewListChat(cs);
                        knowledge.renewListChat(currentCampaign);
                        return;
                    }
                }

                currentCampaign.activeDm.instrumentsCollection.set(currentCampaign.activeDm.editIndex,
                        currentCampaign.activeDm.editInstruments);
            }
            case KIT -> {
                switch (currentCampaign.activeDm.editParameter) {
                    case "-n" -> currentCampaign.activeDm.editKit.setName(response);
                    case "-s" -> currentCampaign.activeDm.editKit.setSummary(response);
                    case "-a" -> currentCampaign.activeDm.editKit.setAmountInInstance(Integer.parseInt(response));
                    case "-v" -> currentCampaign.activeDm.editKit.setValue(Integer.parseInt(response));
                    case "-c" -> currentCampaign.activeDm.editKit.setCurrencyGrade(CurrencyDnD.getCurrency(response));
                    case "-w" ->  currentCampaign.activeDm.editKit.setWeight(Integer.parseInt(response));
                    default -> {
                        walkieTalkie.patternExecute(cs, "Введите корректный параметр.");
                        cs.currentContext = CurrentProcess.FREE;
                        currentCampaign.activeDm.editParameter = "";
                        knowledge.renewListChat(cs);
                        knowledge.renewListChat(currentCampaign);
                        return;
                    }
                }

                currentCampaign.activeDm.kitCollection.set(currentCampaign.activeDm.editIndex,
                        currentCampaign.activeDm.editKit);
            }
            case FEAT -> {
                switch (currentCampaign.activeDm.editParameter) {
                    case "-tl" -> currentCampaign.activeDm.editFeat.setTitle(response);
                    case "-s" -> currentCampaign.activeDm.editFeat.setSummary(response);
                    default -> {
                        walkieTalkie.patternExecute(cs, "Введите корректный параметр.");
                        cs.currentContext = CurrentProcess.FREE;
                        currentCampaign.activeDm.editParameter = "";
                        knowledge.renewListChat(cs);
                        knowledge.renewListChat(currentCampaign);
                        return;
                    }
                }

                currentCampaign.activeDm.featCollection.set(currentCampaign.activeDm.editIndex,
                        currentCampaign.activeDm.editFeat);
            }
            case ABILITY -> {
                switch (currentCampaign.activeDm.editParameter) {
                    case "-tl" -> currentCampaign.activeDm.editAbility.setTitle(response);
                    case "-s" -> currentCampaign.activeDm.editAbility.setSummary(response);
                    case "-l" -> currentCampaign.activeDm.editAbility.setLevel(Integer.parseInt(response));
                    case "-aj" -> currentCampaign.activeDm.editAbility.addJobs(JobsDnD.getJob(response));
                    case "-dj" -> currentCampaign.activeDm.editAbility.delJobs(JobsDnD.getJob(response));
                    case "-apj" -> currentCampaign.activeDm.editAbility.addPrestigeJob(response);
                    case "-dpj" -> currentCampaign.activeDm.editAbility.delPrestigeJob(response);
                    default -> {
                        walkieTalkie.patternExecute(cs, "Введите корректный параметр.");
                        cs.currentContext = CurrentProcess.FREE;
                        currentCampaign.activeDm.editParameter = "";
                        knowledge.renewListChat(cs);
                        knowledge.renewListChat(currentCampaign);
                        return;
                    }
                }

                currentCampaign.activeDm.abilityCollection.set(currentCampaign.activeDm.editIndex,
                        currentCampaign.activeDm.editAbility);
            }
            case SPELL -> {
                switch (currentCampaign.activeDm.editParameter) {
                    case "-tl" -> currentCampaign.activeDm.editSpell.setTitle(response);
                    case "-s" -> currentCampaign.activeDm.editSpell.setSummary(response);
                    case "-l" -> currentCampaign.activeDm.editSpell.setLevel(Integer.parseInt(response));
                    case "-aj" -> currentCampaign.activeDm.editSpell.addJobs(JobsDnD.getJob(response));
                    case "-dj" -> currentCampaign.activeDm.editSpell.delJobs(JobsDnD.getJob(response));
                    case "-apj" -> currentCampaign.activeDm.editSpell.addPrestigeJob(response);
                    case "-dpj" -> currentCampaign.activeDm.editSpell.delPrestigeJob(response);
                    case "-sc" -> currentCampaign.activeDm.editSpell.setSchool(response);
                    case "-st" -> currentCampaign.activeDm.editSpell.setSetupTime(response);
                    case "-d" -> currentCampaign.activeDm.editSpell.setDistance(Double.parseDouble(response));
                    case "-acm" -> currentCampaign.activeDm.editSpell.addComponent(SpellComponentsDnD.getComp(response));
                    case "-dcm" -> currentCampaign.activeDm.editSpell.delComponent(SpellComponentsDnD.getComp(response));
                    case "-dur" -> currentCampaign.activeDm.editSpell.setDuration(response);
                    default -> {
                        walkieTalkie.patternExecute(cs, "Введите корректный параметр.");
                        cs.currentContext = CurrentProcess.FREE;
                        currentCampaign.activeDm.editParameter = "";
                        knowledge.renewListChat(cs);
                        knowledge.renewListChat(currentCampaign);
                        return;
                    }
                }

                currentCampaign.activeDm.spellCollection.set(currentCampaign.activeDm.editIndex,
                        currentCampaign.activeDm.editSpell);
            }
            default -> {
                walkieTalkie.patternExecute(cs, "Выберите предмет.");
                cs.currentContext = CurrentProcess.FREE;
                currentCampaign.activeDm.editParameter = "";
                knowledge.renewListChat(currentCampaign);
                knowledge.renewListChat(cs);
                return;
            }
        }

        walkieTalkie.patternExecute(cs, "Изменение произведено успешно.");
        cs.currentContext = CurrentProcess.FREE;
        currentCampaign.activeDm.editParameter = "";
        knowledge.renewListChat(cs);
        knowledge.renewListChat(currentCampaign);
    }

    public void deleteAspect(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null || secretMessages.isNotLegal(ctx, "par_item1-int2-dm")) {
            return;
        }

        int index = Integer.parseInt(ctx.secondArg()) - 1;
        if (index < 0) {
            walkieTalkie.patternExecute(currentUser,
                    "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                            "Проверьте правильность ввода по справке в /help [команда]");
            return;
        }

        switch (ctx.firstArg()) {
            case "-i" -> {
                if (index >= currentCampaign.activeDm.itemCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }
                currentCampaign.activeDm.itemCollection.remove(index);
                currentCampaign.activeDm.itemCollectionNames.remove(index);
            }
            case "-w" -> {
                if (index >= currentCampaign.activeDm.weaponCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }
                currentCampaign.activeDm.weaponCollection.remove(index);
                currentCampaign.activeDm.weaponCollectionNames.remove(index);
            }
            case "-a" -> {
                if (index >= currentCampaign.activeDm.armorCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }
                currentCampaign.activeDm.armorCollection.remove(index);
                currentCampaign.activeDm.armorCollectionNames.remove(index);
            }
            case "-in" -> {
                if (index >= currentCampaign.activeDm.instrumentsCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }
                currentCampaign.activeDm.instrumentsCollection.remove(index);
                currentCampaign.activeDm.instrumentsCollectionNames.remove(index);
            }
            case "-k" -> {
                if (index >= currentCampaign.activeDm.kitCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }
                currentCampaign.activeDm.kitCollection.remove(index);
                currentCampaign.activeDm.kitCollectionNames.remove(index);
            }
            case "-f" -> {
                if (index >= currentCampaign.activeDm.featCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }
                currentCampaign.activeDm.featCollection.remove(index);
                currentCampaign.activeDm.featCollectionNames.remove(index);
            }
            case "-ab" -> {
                if (index >= currentCampaign.activeDm.abilityCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }
                currentCampaign.activeDm.abilityCollection.remove(index);
                currentCampaign.activeDm.abilityCollectionNames.remove(index);
            }
            case "-sp" -> {
                if (index >= currentCampaign.activeDm.spellCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }
                currentCampaign.activeDm.spellCollection.remove(index);
                currentCampaign.activeDm.spellCollectionNames.remove(index);
            }
            default -> {
                walkieTalkie.patternExecute(currentUser, "Введите корректный параметр.");
                knowledge.renewListChat(currentCampaign);
                knowledge.renewListChat(currentUser);
                return;
            }
        }

        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(currentUser);
    }

    public void giveAspect(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null || secretMessages.isNotLegal(ctx, "par_item1-int2-prof3-dm")) {
            return;
        }

        int index = Integer.parseInt(ctx.secondArg()) - 1;
        switch (ctx.firstArg()) {
            case "-i" -> {
                if (index < 0 || index >= currentCampaign.activeDm.itemCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }
                if (currentCampaign.activeDm.lockVault) {
                    currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).itemCollectionOnHands
                        .add(currentCampaign.activeDm.itemCollection.get(index));
                }
                else {
                    currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).itemCollection
                        .add(currentCampaign.activeDm.itemCollection.get(index));
                }
            }
            case "-w" -> {
                if (index < 0 || index >= currentCampaign.activeDm.weaponCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }
                if (currentCampaign.activeDm.lockVault) {
                    currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).weaponCollectionOnHands
                        .add(currentCampaign.activeDm.weaponCollection.get(index));
                }
                else {
                    currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).weaponCollection
                        .add(currentCampaign.activeDm.weaponCollection.get(index));
                }
            }
            case "-a" -> {
                if (index < 0 || index >= currentCampaign.activeDm.armorCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }
                if (currentCampaign.activeDm.lockVault) {
                    currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).armorCollectionOnHands
                        .add(currentCampaign.activeDm.armorCollection.get(index));
                }
                else {
                    currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).armorCollection
                        .add(currentCampaign.activeDm.armorCollection.get(index));
                }
            }
            case "-in" -> {
                if (index < 0 || index >= currentCampaign.activeDm.instrumentsCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }
                if (currentCampaign.activeDm.lockVault) {
                    currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).instrumentsCollectionOnHands
                        .add(currentCampaign.activeDm.instrumentsCollection.get(index));
                }
                else {
                    currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).instrumentsCollection
                        .add(currentCampaign.activeDm.instrumentsCollection.get(index));
                }
            }
            case "-k" -> {
                if (index < 0 || index >= currentCampaign.activeDm.kitCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }
                if (currentCampaign.activeDm.lockVault) {
                    currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).kitCollectionOnHands
                        .add(currentCampaign.activeDm.kitCollection.get(index));
                }
                else {
                    currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).kitCollection
                        .add(currentCampaign.activeDm.kitCollection.get(index));
                }
            }
            case "-f" -> {
                if (index < 0 || index >= currentCampaign.activeDm.featCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }
                currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).feats
                        .add(currentCampaign.activeDm.featCollection.get(index));
            }
            case "t" -> {
                if (index < 0 || index >= currentCampaign.activeDm.featCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }
                currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).traits
                        .add(currentCampaign.activeDm.featCollection.get(index));
            }
            case "-ab" -> {
                if (index < 0 || index >= currentCampaign.activeDm.abilityCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }
                currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).abilities
                        .add(currentCampaign.activeDm.abilityCollection.get(index));
            }
            case "-sp" -> {
                if (index < 0 || index >= currentCampaign.activeDm.spellCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }
                if (currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).spellBook == null) {
                    walkieTalkie.patternExecute(currentUser, "Данный персонаж не владеет магией.");
                    return;
                }

                currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).spellBook
                        .learnedSpells.add(currentCampaign.activeDm.spellCollection.get(index));
            }
            default -> {
                walkieTalkie.patternExecute(currentUser, "Введите корректный параметр.");
                knowledge.renewListChat(currentCampaign);
                knowledge.renewListChat(currentUser);
                return;
            }
        }

        walkieTalkie.patternExecute(currentUser, "Передача прошла успешно.");
        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(currentUser);
    }

    public void takeAspect(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null || secretMessages.isNotLegal(ctx, "par_item1-int2-prof3-dm")) {
            return;
        }

        int index = Integer.parseInt(ctx.secondArg()) - 1;
        if (index < 0) {
            walkieTalkie.patternExecute(currentUser,
                    "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                            "Проверьте правильность ввода по справке в /help [команда]",
                    null, false);
            return;
        }

        switch (ctx.firstArg()) {
            case "-i" -> {
                if (index >= currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).itemCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }
                currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).itemCollection
                        .remove(index);
            }
            case "-w" -> {
                if (index >= currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).weaponCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }
                currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).weaponCollection
                        .remove(index);
            }
            case "-a" -> {
                if (index >= currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).armorCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }
                currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).armorCollection
                        .remove(index);
            }
            case "-in" -> {
                if (index >= currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).instrumentsCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }
                currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).instrumentsCollection
                        .remove(index);
            }
            case "-k" -> {
                if (index >= currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).kitCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }
                currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).kitCollection
                        .remove(index);
            }
            case "-f" -> {
                if (index >= currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).feats.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }
                currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).feats
                        .remove(index);
            }
            case "t" -> {
                if (index >= currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).traits.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }
                currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).traits
                        .remove(index);
            }
            case "-ab" -> {
                if (index >= currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).abilities.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }
                currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).abilities
                        .remove(index);
            }
            case "-sp" -> {
                if (currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).spellBook == null) {
                    walkieTalkie.patternExecute(currentUser, "Данный персонаж не владеет магией.");
                    return;
                }
                if (index >= currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).spellBook.learnedSpells.size()) {
                    walkieTalkie.patternExecute(currentUser,
                        "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                    return;
                }

                currentCampaign.activeDm.campaignParty.get(ctx.thirdArg()).spellBook
                        .learnedSpells.remove(index);
            }
            default -> {
                walkieTalkie.patternExecute(currentUser, "Введите корректный параметр.");
                knowledge.renewListChat(currentCampaign);
                knowledge.renewListChat(currentUser);
                return;
            }
        }

        walkieTalkie.patternExecute(currentUser, "Передача прошла успешно.");
        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(currentUser);
    }

    public void lockInventory(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null || secretMessages.isNotLegal(ctx, "dm")) {
            return;
        }

        currentCampaign.activeDm.lockVault = !currentCampaign.activeDm.lockVault;
        if (currentCampaign.activeDm.lockVault) {
            walkieTalkie.patternExecute(currentUser, "Вы закрыли хранилище предметов.");
        }
        else {
            walkieTalkie.patternExecute(currentUser, "Вы открыли хранилище предметов.");
        }

        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(currentUser);
    }

    public void bringAspectAlong(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null || secretMessages.isNotLegal(ctx, "int<2")) {
            return;
        }

        if (currentCampaign.activeDm.lockVault) {
            walkieTalkie.patternExecute(currentUser, "Хранилище предметов закрыто - вы не можете ничего оттуда взять.");
            return;
        }

        int index = Integer.parseInt(ctx.secondArg()) - 1;
        String itemName;
        PlayerDnD affectedPlayer = currentCampaign.activeDm.campaignParty.get(currentUser.username);
        switch (ctx.firstArg()) {
            case "-i" -> {
                if (index >= affectedPlayer.itemCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                            "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                    "Проверьте правильность ввода по справке в /help [команда]",
                            null, false);
                    return;
                }
                if (affectedPlayer.currentCarryingCapacity
                        + affectedPlayer.itemCollection.get(index).weight > affectedPlayer.maxCarryingCapacity) {
                    walkieTalkie.patternExecute(currentUser,
                            "Ваш персонаж перегружен, и больше ничего взять не может.",
                            null, false);
                    return;
                }

                itemName = affectedPlayer.itemCollection.get(index).name;
                affectedPlayer.currentCarryingCapacity += affectedPlayer.itemCollection.get(index).weight;
                affectedPlayer.itemCollectionOnHands.add(affectedPlayer.itemCollection.get(index));
                affectedPlayer.itemCollection.remove(index);
            }
            case "-w" -> {
                if (index >= affectedPlayer.weaponCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                            "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                    "Проверьте правильность ввода по справке в /help [команда]",
                            null, false);
                    return;
                }

                itemName = affectedPlayer.weaponCollection.get(index).name;
                affectedPlayer.currentCarryingCapacity += affectedPlayer.weaponCollection.get(index).weight;
                affectedPlayer.weaponCollectionOnHands.add(affectedPlayer.weaponCollection.get(index));
                affectedPlayer.weaponCollection.remove(index);
            }
            case "-a" -> {
                if (index >= affectedPlayer.armorCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                            "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                    "Проверьте правильность ввода по справке в /help [команда]",
                            null, false);
                    return;
                }

                itemName = affectedPlayer.armorCollection.get(index).name;
                affectedPlayer.currentCarryingCapacity += affectedPlayer.armorCollection.get(index).weight;
                affectedPlayer.armorCollectionOnHands.add(affectedPlayer.armorCollection.get(index));
                affectedPlayer.armorCollection.remove(index);
            }
            case "-in" -> {
                if (index >= affectedPlayer.instrumentsCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                            "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                    "Проверьте правильность ввода по справке в /help [команда]",
                            null, false);
                    return;
                }

                itemName = affectedPlayer.instrumentsCollection.get(index).name;
                affectedPlayer.currentCarryingCapacity += affectedPlayer.instrumentsCollection.get(index).weight;
                affectedPlayer.instrumentsCollectionOnHands.add(affectedPlayer.instrumentsCollection.get(index));
                affectedPlayer.instrumentsCollection.remove(index);
            }
            case "-k" -> {
                if (index >= affectedPlayer.kitCollection.size()) {
                    walkieTalkie.patternExecute(currentUser,
                            "Произошла ошибка - введено число вне возможного набора индексов.\n" +
                                    "Проверьте правильность ввода по справке в /help [команда]",
                            null, false);
                    return;
                }

                itemName = affectedPlayer.kitCollection.get(index).name;
                affectedPlayer.currentCarryingCapacity += affectedPlayer.kitCollection.get(index).weight;
                affectedPlayer.kitCollectionOnHands.add(affectedPlayer.kitCollection.get(index));
                affectedPlayer.kitCollection.remove(index);
            }
            default -> {
                walkieTalkie.patternExecute(currentUser, "Введите корректный параметр.");
                knowledge.renewListChat(currentCampaign);
                knowledge.renewListChat(currentUser);
                return;
            }
        }

        currentCampaign.activeDm.campaignParty.put(currentUser.username, affectedPlayer);
        walkieTalkie.patternExecute(ctx,
                "Вы успешно взяли предмет " + itemName + " с собой.");
        knowledge.renewListChat(currentCampaign);
        knowledge.renewListChat(currentUser);
    }

    public void equipActiveAspect(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null || secretMessages.isNotLegal(ctx, "par_item1-int<2")) {
            return;
        }

        int index = Integer.parseInt(ctx.secondArg()) - 1;
        PlayerDnD affectedPlayer = currentCampaign.activeDm.campaignParty.get(currentUser.username);
        switch (ctx.firstArg()) {
            case "-w" -> {
                if (index >= affectedPlayer.weaponCollectionOnHands.size()) {
                    walkieTalkie.patternExecute(currentUser,
                            "Произошла ошибка - введено число вне возможного набора индексов.\n"
                                    + "Попробуйте заново.");
                    return;
                }

                WeaponDnD requestedWeapon = affectedPlayer.weaponCollectionOnHands.get(index);
                if (!ItemsIdsDnD.isMastered(affectedPlayer.weaponProficiency, requestedWeapon.id)
                        && !ItemsIdsDnD.isMastered(affectedPlayer.weaponProficiency, requestedWeapon.type)
                        && !ItemsIdsDnD.isMastered(affectedPlayer.weaponProficiency, requestedWeapon.range)) {
                    walkieTalkie.patternExecute(currentUser, "Ваш персонаж не владеет данным типом оружия.",
                            null, false);
                    return;
                }

                affectedPlayer.equippedWeapon = affectedPlayer.weaponCollectionOnHands.get(index);
                affectedPlayer.equippedWeaponIndex = index;

                affectedPlayer.hitBonus = affectedPlayer.equippedWeapon.hitBonus;
                affectedPlayer.weaponEffects = affectedPlayer.equippedWeapon.effects;

                affectedPlayer.attackDice = affectedPlayer.equippedWeapon.attackDice;
                affectedPlayer.damageType = affectedPlayer.equippedWeapon.damageType;

                walkieTalkie.patternExecute(currentUser, "Оружие "
                        + affectedPlayer.equippedWeapon.name
                        + " было установлено.");
            }
            case "-a" -> {
                if (index >= affectedPlayer.armorCollectionOnHands.size()) {
                    walkieTalkie.patternExecute(currentUser,
                            "Произошла ошибка - введено число вне возможного набора индексов.\n"
                                    + "Попробуйте заново.");
                    return;
                }

                ArmorDnD requestedArmor = affectedPlayer.armorCollectionOnHands.get(index);
                if (!affectedPlayer.armorProficiency.contains(requestedArmor.type)) {
                    walkieTalkie.patternExecute(currentUser,
                            "Данная броня не подходит под вас (Ваш класс не позволяет надеть данный тип брони)",
                            null, false);
                }
                if (requestedArmor.strengthRequirement > affectedPlayer.strength) {
                    walkieTalkie.patternExecute(currentUser,
                            "Данная броня не подходит под вас (Вашей силы не хватает для ношения данной брони)",
                            null, false);
                }

                affectedPlayer.equippedArmor = requestedArmor;
                affectedPlayer.armorClass = requestedArmor.armorClass + Integer.min(affectedPlayer.dexterityModifier,
                        requestedArmor.dexterityModMax);
                if (affectedPlayer.equippedArmor.hasStealthDisadvantage) {
                    affectedPlayer.externalDisadvantages.add(MasteryTypeDnD.STEALTH);
                }
                affectedPlayer.equippedArmorIndex = index;
                affectedPlayer.armorEffects = affectedPlayer.equippedArmor.effects;

                walkieTalkie.patternExecute(currentUser, "Броня "
                        + affectedPlayer.equippedArmor.name
                        + " была установлена.");
            }
            case "-i" -> {
                if (index >= affectedPlayer.itemCollectionOnHands.size()) {
                    walkieTalkie.patternExecute(currentUser,
                            "Произошла ошибка - введено число вне возможного набора индексов.\n"
                                    + "Попробуйте заново.");
                    return;
                }

                ItemDnD requestedItem = affectedPlayer.itemCollectionOnHands.get(index);
                affectedPlayer.attunedAccessories.add(requestedItem);

                walkieTalkie.patternExecute(currentUser, "Предмет "
                        + affectedPlayer.attunedAccessories.getLast().name
                        + " был настроен на вас.");
            }
            default -> {
                walkieTalkie.patternExecute(ctx,
                        "Произошла ошибка - такого параметра у команды нет.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                knowledge.renewListChat(currentUser);
                knowledge.renewListChat(currentCampaign);
                return;
            }
        }

        currentCampaign.activeDm.campaignParty.put(currentUser.username, affectedPlayer);
        knowledge.renewListChat(currentUser);
        knowledge.renewListChat(currentCampaign);
    }

    public void removeActiveAspect(MessageContext ctx) {
        ChatSession currentUser = knowledge.getSession(ctx.chatId().toString());
        ChatSession currentCampaign = getCampaignSession(currentUser);
        if (currentCampaign == null || secretMessages.isNotLegal(ctx, "par_item1")) {
            return;
        }

        PlayerDnD affectedPlayer = currentCampaign.activeDm.campaignParty.get(currentUser.username);
        switch (ctx.firstArg()) {
            case "-w" -> {
                walkieTalkie.patternExecute(currentUser, "Оружие "
                        + affectedPlayer.equippedWeapon.name
                        + " было снято.");

                affectedPlayer.equippedWeapon = null;
                affectedPlayer.equippedArmorIndex = 0;
                affectedPlayer.weaponEffects = "Нет.";
                affectedPlayer.attackDice = "1d4";
                affectedPlayer.damageType = DamageTypeDnD.BLUDGEONING;
            }
            case "-a" -> {
                if (affectedPlayer.equippedArmor.hasStealthDisadvantage) {
                    affectedPlayer.externalDisadvantages.remove(MasteryTypeDnD.STEALTH);
                }

                walkieTalkie.patternExecute(currentUser, "Броня "
                        + affectedPlayer.equippedArmor.name
                        + " была снята.");

                affectedPlayer.equippedArmor = null;
                affectedPlayer.equippedArmorIndex = 0;
                affectedPlayer.armorClass = 10;
                affectedPlayer.armorEffects = "Нет.";
            }
            default -> {
                walkieTalkie.patternExecute(ctx,
                        "Произошла ошибка - такого параметра у команды нет.\n" +
                                "Проверьте правильность ввода по справке в /help [команда]");
                knowledge.renewListChat(currentUser);
                knowledge.renewListChat(currentCampaign);
                return;
            }
        }
        currentCampaign.activeDm.campaignParty.put(currentUser.username, affectedPlayer);
        knowledge.renewListChat(currentUser);
        knowledge.renewListChat(currentCampaign);
    }

    //данные, по которым идет поиск предметов (буквально их источник)
    private final HashMap<String, ItemDnD> itemAllocator = new HashMap<>();
    private final HashMap<String, WeaponDnD> weaponAllocator = new HashMap<>();
    private final HashMap<String, ArmorDnD> armorAllocator = new HashMap<>();
    private final HashMap<String, InstrumentDnD> instrumentsAllocator = new HashMap<>();
    private final HashMap<String, KitDnD> kitAllocator = new HashMap<>();
    private final HashMap<String, FeatDnD> featAllocator = new HashMap<>();
    private final HashMap<String, AbilityDnD> abilityAllocator = new HashMap<>();
    private final HashMap<String, SpellDnD> spellAllocator = new HashMap<>();

    public ArrayList<String> searchAspects(String askedAspect, String parameter) {
        ArrayList<String> searchedAspects = new ArrayList<>();
        Set<String> aspects;

        switch (parameter) {
            case "-i" -> aspects = itemAllocator.keySet();
            case "-w" -> aspects = weaponAllocator.keySet();
            case "-a" -> aspects = armorAllocator.keySet();
            case "-in" -> aspects = instrumentsAllocator.keySet();
            case "-k" -> aspects = kitAllocator.keySet();
            case "-f" -> aspects = featAllocator.keySet();
            case "-ab" -> aspects = abilityAllocator.keySet();
            case "-sp" -> aspects = spellAllocator.keySet();
            default -> {
                return searchedAspects;
            }
        }

        for (String aspect : aspects) {
            if (aspect.contains(askedAspect)) {
                searchedAspects.add(aspect);
            }
        }

        return searchedAspects;
    }

    public String searchAspect(String askedAspect, String parameter) {
        Set<String> aspects;
        int distance;

        LevenshteinDistance env = new LevenshteinDistance();
        int minSimilarityDistance = 1999999999;
        String resAspect = "";

        switch (parameter) {
            case "-i" -> aspects = itemAllocator.keySet();
            case "-w" -> aspects = weaponAllocator.keySet();
            case "-a" -> aspects = armorAllocator.keySet();
            case "-in" -> aspects = instrumentsAllocator.keySet();
            case "-k" -> aspects = kitAllocator.keySet();
            case "-f" -> aspects = featAllocator.keySet();
            case "-ab" -> aspects = abilityAllocator.keySet();
            case "-sp" -> aspects = spellAllocator.keySet();
            default -> {
                return resAspect;
            }
        }

        for (String item: aspects) {
            distance = env.apply(item.toLowerCase(), askedAspect.toLowerCase());
            if (distance < minSimilarityDistance) {
                resAspect = item;
                minSimilarityDistance = distance;
            }
        }

        return resAspect;
    }

    public String aspectSearchWriter(ArrayList<String> aspects) {
        StringBuilder result = new StringBuilder();
        result.append("Результаты поиска:").append("\n");

        for (String item : aspects) {
            result.append(item).append("\n");
        }

        result.append("Введите имя того, что вы хотите добавить в свой набор.");
        return result.toString();
    }

    public String aspectStorageWriter(ArrayList<String> aspects) {
        StringBuilder result = new StringBuilder();
        result.append("На данный момент у вас есть:").append("\n");

        for (int i = 1; i < aspects.size() + 1; i++) {
            result.append(i).append(". ").append(aspects.get(i - 1)).append("\n");
        }

        return result.toString();
    }

    public DnDItemHandler(DataHandler knowledge, TextHandler walkieTalkie, SiteParseHandler archive, DnDNotificationHandler secretMessages) {
        this.knowledge = knowledge;
        this.walkieTalkie = walkieTalkie;
        this.archive = archive;
        this.secretMessages = secretMessages;

        itemAllocator.put("Новый предмет", new ItemDnD());

        itemAllocator.put("Абак", new ItemDnD.AbacusDnD());
        itemAllocator.put("Флакон кислоты", new ItemDnD.VialOfAcidDnD());
        itemAllocator.put("Фляга алхимического огня", new ItemDnD.FlaskOfAlchemistFireDnD());

        itemAllocator.put("Стрела", new ItemDnD.ArrowDnD());
        itemAllocator.put("Игла для духовой трубки", new ItemDnD.BlowgunNeedleDnD());
        itemAllocator.put("Арбалетный болт", new ItemDnD.CrossbowBoltDnD());
        itemAllocator.put("Снаряд для пращи", new ItemDnD.SlingBulletDnD());

        itemAllocator.put("Флакон противоядия", new ItemDnD.VialOfAntitoxinDnD());

        itemAllocator.put("Кристалл", new ItemDnD.CrystalDnD());
        itemAllocator.put("Сфера", new ItemDnD.OrbDnD());
        itemAllocator.put("Жезл", new ItemDnD.RodDnD());
        itemAllocator.put("Посох", new ItemDnD.StaffDnD());
        itemAllocator.put("Волшебная палочка", new ItemDnD.WandDnD());

        itemAllocator.put("Рюкзак", new ItemDnD.BackpackDnD());
        itemAllocator.put("Металлический шарик", new ItemDnD.BallBearingDnD());
        itemAllocator.put("Бочка", new ItemDnD.BarrelDnD());
        itemAllocator.put("Корзина", new ItemDnD.BasketDnD());
        itemAllocator.put("Спальник", new ItemDnD.BedrollDnD());
        itemAllocator.put("Колокольчик", new ItemDnD.BellDnD());
        itemAllocator.put("Одеяло", new ItemDnD.BlanketDnD());
        itemAllocator.put("Блок и лебедка", new ItemDnD.BlockAndTackleDnD());
        itemAllocator.put("Книга", new ItemDnD.BookDnD());
        itemAllocator.put("Стеклянная бутылка", new ItemDnD.GlassBottleDnD());
        itemAllocator.put("Ведро", new ItemDnD.BucketDnD());
        itemAllocator.put("Калтроп", new ItemDnD.CaltropDnD());
        itemAllocator.put("Свеча", new ItemDnD.CandleDnD());
        itemAllocator.put("Контейнер для арбалетных болтов", new ItemDnD.CaseForCrossbowBoltsDnD());
        itemAllocator.put("Контейнер для карт и свитков", new ItemDnD.CaseForMapsAndScrollsDnD());
        itemAllocator.put("Цепь (10 футов)", new ItemDnD.ChainDnD());
        itemAllocator.put("Мел", new ItemDnD.ChalkDnD());
        itemAllocator.put("Сундук", new ItemDnD.ChestDnD());
        itemAllocator.put("Комплект для лазания", new ItemDnD.ClimberKitDnD());
        itemAllocator.put("Обычная одежда", new ItemDnD.CommonClothesDnD());
        itemAllocator.put("Костюм", new ItemDnD.CostumeClothesDnD());
        itemAllocator.put("Отличная одежда", new ItemDnD.FineClothesDnD());
        itemAllocator.put("Дорожная одежда", new ItemDnD.TravelerClothesDnD());
        itemAllocator.put("Мешочек с компонентами", new ItemDnD.ComponentPouchDnD());
        itemAllocator.put("Ломик", new ItemDnD.CrowbarDnD());

        itemAllocator.put("Веточка омелы", new ItemDnD.SprigOfMistletoeDnD());
        itemAllocator.put("Тотем", new ItemDnD.TotemDnD());
        itemAllocator.put("Деревянный посох", new ItemDnD.WoodenStaffDnD());
        itemAllocator.put("Тисовая палочка", new ItemDnD.YewWandDnD());

        itemAllocator.put("Комплект для рыбалки", new ItemDnD.FishingTackleDnD());
        itemAllocator.put("Фляга или большая кружка", new ItemDnD.FlaskOrTankardDnD());
        itemAllocator.put("Крюк-кошка", new ItemDnD.GrapplingHookDnD());
        itemAllocator.put("Молоток", new ItemDnD.HammerDnD());
        itemAllocator.put("Кузнечный молот", new ItemDnD.SledgehammerDnD());
        itemAllocator.put("Комплект целителя", new ItemDnD.HealerKitDnD());

        itemAllocator.put("Амулет", new ItemDnD.AmuletDnD());
        itemAllocator.put("Эмблема", new ItemDnD.EmblemDnD());
        itemAllocator.put("Реликварий", new ItemDnD.ReliquaryDnD());

        itemAllocator.put("Фляга святой воды", new ItemDnD.FlaskOfHolyWaterDnD());
        itemAllocator.put("Песочные часы", new ItemDnD.HourglassDnD());
        itemAllocator.put("Охотничий капкан", new ItemDnD.HuntingTrapDnD());
        itemAllocator.put("Чернила (бутылочка на 30 грамм)", new ItemDnD.InkDnD());
        itemAllocator.put("Писчее перо", new ItemDnD.InkPenDnD());
        itemAllocator.put("Кувшин или графин", new ItemDnD.JugOrPitcherDnD());
        itemAllocator.put("Лестница (10 футов)", new ItemDnD.LadderDnD());
        itemAllocator.put("Лампа", new ItemDnD.LampDnD());
        itemAllocator.put("Направленный фонарь", new ItemDnD.BullseyeLanternDnD());
        itemAllocator.put("Закрытый фонарь", new ItemDnD.HoodedLanternDnD());
        itemAllocator.put("Замок", new ItemDnD.LockDnD());
        itemAllocator.put("Увеличительное стекло", new ItemDnD.MagnifyingGlassDnD());
        itemAllocator.put("Кандалы", new ItemDnD.ManaclesDnD());
        itemAllocator.put("Столовый набор", new ItemDnD.MessKitDnD());
        itemAllocator.put("Стальное зеркало", new ItemDnD.SteelMirrorDnD());
        itemAllocator.put("Фляга масла", new ItemDnD.FlaskOfOilDnD());
        itemAllocator.put("Бумага", new ItemDnD.PaperDnD());
        itemAllocator.put("Пергамент", new ItemDnD.ParchmentDnD());
        itemAllocator.put("Флакон духов", new ItemDnD.VialOfPerfumeDnD());
        itemAllocator.put("Горняцкая кирка", new ItemDnD.MinersPickDnD());
        itemAllocator.put("Шлямбур", new ItemDnD.PitonDnD());
        itemAllocator.put("Флакон простого яда", new ItemDnD.VialOfBasicPoisonDnD());
        itemAllocator.put("Шест (10 футов)", new ItemDnD.PoleDnD());
        itemAllocator.put("Железный горшок", new ItemDnD.IronPotDnD());
        itemAllocator.put("Зелье лечения", new ItemDnD.PotionOfHealingDnD());
        itemAllocator.put("Кошель", new ItemDnD.PouchDnD());
        itemAllocator.put("Колчан", new ItemDnD.QuiverDnD());
        itemAllocator.put("Портативный таран", new ItemDnD.PortableRamDnD());
        itemAllocator.put("Рационы (1 день)", new ItemDnD.RationsDnD());
        itemAllocator.put("Ряса", new ItemDnD.RobesDnD());
        itemAllocator.put("Пеньковая веревка (50 футов)", new ItemDnD.HempenRopeDnD());
        itemAllocator.put("Шелковая веревка (50 футов)", new ItemDnD.SilkRopeDnD());
        itemAllocator.put("Мешок", new ItemDnD.SackDnD());
        itemAllocator.put("Торговые весы", new ItemDnD.MerchantScaleDnD());
        itemAllocator.put("Воск", new ItemDnD.SealingWaxDnD());
        itemAllocator.put("Лопата", new ItemDnD.ShovelDnD());
        itemAllocator.put("Сигнальный свисток", new ItemDnD.SignalWhistleDnD());
        itemAllocator.put("Кольцо-печатка", new ItemDnD.SignetRingDnD());
        itemAllocator.put("Мыло", new ItemDnD.SoapDnD());
        itemAllocator.put("Книга заклинаний", new ItemDnD.SpellbookDnD());
        itemAllocator.put("Железный шип", new ItemDnD.IronSpikeDnD());
        itemAllocator.put("Подзорная труба", new ItemDnD.SpyglassDnD());
        itemAllocator.put("Двухместная палатка", new ItemDnD.TwoPersonTentDnD());
        itemAllocator.put("Трутница", new ItemDnD.TinderboxDnD());
        itemAllocator.put("Факел", new ItemDnD.TorchDnD());
        itemAllocator.put("Флакон", new ItemDnD.VialDnD());
        itemAllocator.put("Бурдюк", new ItemDnD.WaterskinDnD());
        itemAllocator.put("Точильный камень", new ItemDnD.WhetstoneDnD());

        weaponAllocator.put("Новое оружие", new WeaponDnD());
        weaponAllocator.put("Боевой топор", new WeaponDnD.BattleaxeDnD());
        weaponAllocator.put("Духовая трубка", new WeaponDnD.BlowgunDnD());
        weaponAllocator.put("Дубинка", new WeaponDnD.ClubDnD());
        weaponAllocator.put("Кинжал", new WeaponDnD.DaggerDnD());
        weaponAllocator.put("Дротик", new WeaponDnD.DartDnD());
        weaponAllocator.put("Цеп", new WeaponDnD.FlailDnD());
        weaponAllocator.put("Глефа", new WeaponDnD.GlaiveDnD());
        weaponAllocator.put("Секира", new WeaponDnD.GreataxeDnD());
        weaponAllocator.put("Палица", new WeaponDnD.GreatclubDnD());
        weaponAllocator.put("Двуручный меч", new WeaponDnD.GreatswordDnD());
        weaponAllocator.put("Алебарда", new WeaponDnD.HalbergDnD());
        weaponAllocator.put("Ручной топор", new WeaponDnD.HandaxeDnD());
        weaponAllocator.put("Ручной арбалет", new WeaponDnD.HandCrossbowDnD());
        weaponAllocator.put("Тяжелый арбалет", new WeaponDnD.HeavyCrossbowDnD());
        weaponAllocator.put("Метательное копье", new WeaponDnD.JavelinDnD());
        weaponAllocator.put("Длинное копье", new WeaponDnD.LanceDnD());
        weaponAllocator.put("Легкий арбалет", new WeaponDnD.LightCrossbowDnD());
        weaponAllocator.put("Легкий молот", new WeaponDnD.LightHammerDnD());
        weaponAllocator.put("Длинный лук", new WeaponDnD.LongbowDnD());
        weaponAllocator.put("Длинный меч", new WeaponDnD.LongswordDnD());
        weaponAllocator.put("Булава", new WeaponDnD.MaceDnD());
        weaponAllocator.put("Молот", new WeaponDnD.MaulDnD());
        weaponAllocator.put("Моргенштерн", new WeaponDnD.MorningstarDnD());
        weaponAllocator.put("Сеть", new WeaponDnD.NetDnD());
        weaponAllocator.put("Пика", new WeaponDnD.PikeDnD());
        weaponAllocator.put("Боевой посох", new WeaponDnD.QuarterstaffDnD());
        weaponAllocator.put("Рапира", new WeaponDnD.RapierDnD());
        weaponAllocator.put("Скимитар", new WeaponDnD.ScimitarDnD());
        weaponAllocator.put("Короткий лук", new WeaponDnD.ShortbowDnD());
        weaponAllocator.put("Короткий меч", new WeaponDnD.ShortswordDnD());
        weaponAllocator.put("Серп", new WeaponDnD.SickleDnD());
        weaponAllocator.put("Праща", new WeaponDnD.SlingDnD());
        weaponAllocator.put("Копье", new WeaponDnD.SpearDnD());
        weaponAllocator.put("Трезубец", new WeaponDnD.TridentDnD());
        weaponAllocator.put("Боевой молот", new WeaponDnD.WarhammerDnD());
        weaponAllocator.put("Боевая кирка", new WeaponDnD.WarPickDnD());
        weaponAllocator.put("Кнут", new WeaponDnD.WhipDnD());

        armorAllocator.put("Новая броня", new ArmorDnD());
        armorAllocator.put("Кираса", new ArmorDnD.BreastplateDnD());
        armorAllocator.put("Кольчуга", new ArmorDnD.ChainMailDnD());
        armorAllocator.put("Кольчужная рубаха", new ArmorDnD.ChainShirtDnD());
        armorAllocator.put("Полулаты", new ArmorDnD.HalfPlateDnD());
        armorAllocator.put("Шкурный доспех", new ArmorDnD.HideArmorDnD());
        armorAllocator.put("Кожаный доспех", new ArmorDnD.LeatherArmorDnD());
        armorAllocator.put("Стёганный доспех", new ArmorDnD.PaddedArmorDnD());
        armorAllocator.put("Латы", new ArmorDnD.PlateArmorDnD());
        armorAllocator.put("Колечный доспех", new ArmorDnD.RingMailDnD());
        armorAllocator.put("Чешуйчатый доспех", new ArmorDnD.ScaleMailDnD());
        armorAllocator.put("Щит", new ArmorDnD.ShieldDnD());
        armorAllocator.put("Шипастые доспехи", new ArmorDnD.SpikedArmorDnD());
        armorAllocator.put("Наборный доспех", new ArmorDnD.SplintArmorDnD());
        armorAllocator.put("Проклепанный кожаный доспех", new ArmorDnD.StuddedLeatherArmorDnD());

        instrumentsAllocator.put("Новые инструменты", new InstrumentDnD());
        instrumentsAllocator.put("Инструменты алхимика", new InstrumentDnD.AlchemistSuppliesDnD());
        instrumentsAllocator.put("Инструменты пивовара", new InstrumentDnD.BrewerSuppliesDnD());
        instrumentsAllocator.put("Инструменты каллиграфа", new InstrumentDnD.CalligrapherSuppliesDnD());
        instrumentsAllocator.put("Инструменты плотника", new InstrumentDnD.CarpenterToolsDnD());
        instrumentsAllocator.put("Инструменты картографа", new InstrumentDnD.CartographerToolsDnD());
        instrumentsAllocator.put("Инструменты сапожника", new InstrumentDnD.CobblerToolsDnD());
        instrumentsAllocator.put("Инструменты повара", new InstrumentDnD.CookUtensilsDnD());
        instrumentsAllocator.put("Инструменты стеклодува", new InstrumentDnD.GlassblowerToolsDnD());
        instrumentsAllocator.put("Инструменты ювелира", new InstrumentDnD.JewelerToolsDnD());
        instrumentsAllocator.put("Инструменты кожевника", new InstrumentDnD.LeatherworkerToolsDnD());
        instrumentsAllocator.put("Инструменты каменщика", new InstrumentDnD.MasonToolsDnD());
        instrumentsAllocator.put("Инструменты художника", new InstrumentDnD.PainterToolsDnD());
        instrumentsAllocator.put("Инструменты гончара", new InstrumentDnD.PotterToolsDnD());
        instrumentsAllocator.put("Инструменты кузнеца", new InstrumentDnD.SmithToolsDnD());
        instrumentsAllocator.put("Инструменты ремонтника", new InstrumentDnD.TinkerToolsDnD());
        instrumentsAllocator.put("Инструменты ткача", new InstrumentDnD.WeaverToolsDnD());
        instrumentsAllocator.put("Инструменты резчика по дереву", new InstrumentDnD.WoodcarverToolsDnD());
        instrumentsAllocator.put("Набор для грима", new InstrumentDnD.DisguiseKitDnD());
        instrumentsAllocator.put("Набор для фальсификации", new InstrumentDnD.ForgeryKitDnD());
        instrumentsAllocator.put("Кости", new InstrumentDnD.DiceSetDnD());
        instrumentsAllocator.put("Драконьи шахматы", new InstrumentDnD.DragonchessSetDnD());
        instrumentsAllocator.put("Карты", new InstrumentDnD.PlayingCardSetDnD());
        instrumentsAllocator.put("Ставка трех драконов", new InstrumentDnD.ThreeDragonAnteSetDnD());
        instrumentsAllocator.put("Набор травника", new InstrumentDnD.HerbalismKitDnD());
        instrumentsAllocator.put("Волынка", new InstrumentDnD.BagpipesDnD());
        instrumentsAllocator.put("Барабан", new InstrumentDnD.DrumDnD());
        instrumentsAllocator.put("Цимбалы", new InstrumentDnD.DulcimerDnD());
        instrumentsAllocator.put("Флейта", new InstrumentDnD.FluteDnD());
        instrumentsAllocator.put("Лютня", new InstrumentDnD.LuteDnD());
        instrumentsAllocator.put("Лира", new InstrumentDnD.LyreDnD());
        instrumentsAllocator.put("Рожок", new InstrumentDnD.HornDnD());
        instrumentsAllocator.put("Свирель", new InstrumentDnD.PanFluteDnD());
        instrumentsAllocator.put("Шалмей", new InstrumentDnD.ShawmDnD());
        instrumentsAllocator.put("Виола", new InstrumentDnD.ViolDnD());
        instrumentsAllocator.put("Инструменты навигатора", new InstrumentDnD.NavigatorToolsDnD());
        instrumentsAllocator.put("Инструменты отравителя", new InstrumentDnD.PoisonerKitDnD());
        instrumentsAllocator.put("Воровские инструменты", new InstrumentDnD.ThievesToolsDnD());
        instrumentsAllocator.put("Транспорт", new InstrumentDnD.VehicleDnD());

        kitAllocator.put("Новый набор", new KitDnD());
        kitAllocator.put("Набор взломщика", new KitDnD.BurglarPackDnD());
        kitAllocator.put("Набор дипломата", new KitDnD.DiplomatPackDnD());
        kitAllocator.put("Набор исследователя подземелий", new KitDnD.DungeoneerPackDnD());
        kitAllocator.put("Набор артиста", new KitDnD.EntertainerKitDnD());
        kitAllocator.put("Набор путешественника", new KitDnD.ExplorerPackDnD());
        kitAllocator.put("Набор священника", new KitDnD.PriestPackDnD());
        kitAllocator.put("Набор ученого", new KitDnD.ScholarPackDnD());

        featAllocator.put("Новая черта", new FeatDnD());

        abilityAllocator.put("Новая способность", new AbilityDnD());

        spellAllocator.put("Новое заклинание", new SpellDnD());
    }
}
