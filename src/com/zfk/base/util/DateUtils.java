package com.zfk.base.util;
/*   2:    */
/*   3:    */import java.text.ParseException;
/*   4:    */import java.text.SimpleDateFormat;
/*   5:    */import java.util.Calendar;
/*   6:    */import java.util.Date;
/*   7:    */import java.util.GregorianCalendar;
/*   8:    */import java.util.Locale;
/*   9:    */import org.apache.commons.lang3.StringUtils;
/*  10:    */import org.apache.commons.lang3.math.NumberUtils;
/*  11:    */import org.apache.commons.lang3.time.DateFormatUtils;
/*  12:    */import org.slf4j.Logger;
/*  13:    */import org.slf4j.LoggerFactory;
/*  14:    */
/*  21:    */public class DateUtils
/*  22:    */  extends org.apache.commons.lang3.time.DateUtils
/*  23:    */{
/*  24: 24 */  private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);
/*  25:    */  
/*  27:    */  private static final long ONE_DAY_INTERVAL = 86400000L;
/*  28:    */  
/*  29: 29 */  private static final String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM" };
/*  30:    */  
/*  31:    */  public static final String MONTH = "yyyy-MM";
/*  32:    */  
/*  33:    */  public static final String DAY = "yyyy-MM-dd";
/*  34:    */  
/*  35:    */  public static final String MINUTE = "yyyy-MM-dd HH:mm";
/*  36:    */  
/*  37:    */  public static final String SECOND = "yyyy-MM-dd HH:mm:ss";
/*  38:    */  
/*  39:    */  public static final String MILLISECOND = "yyyy-MM-dd HH:mm:ss SSSS";
/*  40:    */  
/*  41:    */  public static final String MONTH_N = "yyyyMM";
/*  42:    */  public static final String DAY_N = "yyyyMMdd";
/*  43:    */  public static final String MINUTE_N = "yyyyMMddHHmm";
/*  44:    */  public static final String SECOND_N = "yyyyMMddHHmmss";
/*  45:    */  public static final String MILLISECOND_N = "yyyyMMddHHmmssSSSS";
/*  46:    */  private static final byte BASE_DATE_FORMAT_LEN = 10;
/*  47:    */  
/*  48:    */  public static String getDate()
/*  49:    */  {
/*  50: 50 */    return getDate("yyyy-MM-dd");
/*  51:    */  }
/*  52:    */  
/*  55:    */  public static String getDate(String pattern)
/*  56:    */  {
/*  57: 57 */    return DateFormatUtils.format(new Date(), pattern);
/*  58:    */  }
/*  59:    */  
/*  62:    */  public static String formatDate(Date date, String pattern)
/*  63:    */  {
/*  64: 64 */    String formatDate = null;
/*  65: 65 */    if (StringUtils.isNotBlank(pattern)) {
/*  66: 66 */      formatDate = DateFormatUtils.format(date, pattern);
/*  67:    */    } else {
/*  68: 68 */      formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
/*  69:    */    }
/*  70: 70 */    return formatDate;
/*  71:    */  }
/*  72:    */  
/*  75:    */  public static String formatDateTime(Date date)
/*  76:    */  {
/*  77: 77 */    return formatDate(date, "yyyy-MM-dd HH:mm:ss");
/*  78:    */  }
/*  79:    */  
/*  82:    */  public static String getTime()
/*  83:    */  {
/*  84: 84 */    return formatDate(new Date(), "HH:mm:ss");
/*  85:    */  }
/*  86:    */  
/*  89:    */  public static String getDateTime()
/*  90:    */  {
/*  91: 91 */    return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
/*  92:    */  }
/*  93:    */  
/*  96:    */  public static String getYear()
/*  97:    */  {
/*  98: 98 */    return formatDate(new Date(), "yyyy");
/*  99:    */  }
/* 100:    */  
/* 103:    */  public static String getMonth()
/* 104:    */  {
/* 105:105 */    return formatDate(new Date(), "MM");
/* 106:    */  }
/* 107:    */  
/* 110:    */  public static String getDay()
/* 111:    */  {
/* 112:112 */    return formatDate(new Date(), "dd");
/* 113:    */  }
/* 114:    */  
/* 117:    */  public static String getWeek()
/* 118:    */  {
/* 119:119 */    return formatDate(new Date(), "E");
/* 120:    */  }
/* 121:    */  
/* 127:    */  public static Date parseDate(Object str)
/* 128:    */  {
/* 129:129 */    if (str == null) {
/* 130:130 */      return null;
/* 131:    */    }
/* 132:    */    try {
/* 133:133 */      return parseDate(str.toString(), parsePatterns);
/* 134:    */    } catch (ParseException e) {}
/* 135:135 */    return null;
/* 136:    */  }
/* 137:    */  
/* 144:    */  public static long pastDays(Date date)
/* 145:    */  {
/* 146:146 */    long t = System.currentTimeMillis() - date.getTime();
/* 147:147 */    return t / 86400000L;
/* 148:    */  }
/* 149:    */  
/* 155:    */  public static long pastHour(Date date)
/* 156:    */  {
/* 157:157 */    long t = System.currentTimeMillis() - date.getTime();
/* 158:158 */    return t / 3600000L;
/* 159:    */  }
/* 160:    */  
/* 166:    */  public static long pastMinutes(Date date)
/* 167:    */  {
/* 168:168 */    long t = System.currentTimeMillis() - date.getTime();
/* 169:169 */    return t / 60000L;
/* 170:    */  }
/* 171:    */  
/* 177:    */  public static String formatDateTime(long timeMillis)
/* 178:    */  {
/* 179:179 */    long day = timeMillis / 86400000L;
/* 180:180 */    long hour = timeMillis / 3600000L - day * 24L;
/* 181:181 */    long min = timeMillis / 60000L - day * 24L * 60L - hour * 60L;
/* 182:182 */    long s = timeMillis / 1000L - day * 24L * 60L * 60L - hour * 60L * 60L - min * 60L;
/* 183:183 */    long sss = timeMillis - day * 24L * 60L * 60L * 1000L - hour * 60L * 60L * 1000L - min * 60L * 1000L - s * 1000L;
/* 184:184 */    return new StringBuilder().append(day > 0L ? new StringBuilder().append(day).append(",").toString() : "").append(hour).append(":").append(min).append(":").append(s).append(".").append(sss).toString();
/* 185:    */  }
/* 186:    */  
/* 193:    */  public static double getDistanceOfTwoDate(Date before, Date after)
/* 194:    */  {
/* 195:195 */    long beforeTime = before.getTime();
/* 196:196 */    long afterTime = after.getTime();
/* 197:197 */    return (afterTime - beforeTime) / 86400000.0D;
/* 198:    */  }
/* 199:    */  
/* 207:    */  public static Date format(String stringDate)
/* 208:    */  {
/* 209:    */    try
/* 210:    */    {
/* 211:211 */      if ((stringDate == null) || ("".equals(stringDate.trim()))) {
/* 212:212 */        return null;
/* 213:    */      }
/* 214:214 */      String format = "yyyy-MM-dd";
/* 215:215 */      String format_long = "yyyy-MM-dd HH:mm:ss";
/* 216:216 */      SimpleDateFormat f = null;
/* 217:217 */      if (format.length() == stringDate.length()) {
/* 218:218 */        f = new SimpleDateFormat(format);
/* 219:219 */      } else if (format_long.length() == stringDate.length()) {
/* 220:220 */        f = new SimpleDateFormat(format_long);
/* 221:    */      }
/* 222:    */      
/* 223:223 */      if (f != null) {
/* 224:224 */        return f.parse(stringDate);
/* 225:    */      }
/* 226:226 */      return null;
/* 227:    */    } catch (Exception e) {
/* 228:228 */      logger.error("转换日期出错", e); }
/* 229:229 */    return null;
/* 230:    */  }
/* 231:    */  
/* 239:    */  public static Date getStartTimeByDay(Date day)
/* 240:    */  {
/* 241:241 */    return format(DateFormatUtils.format(new Date(), "yyyy-MM-dd 00:00:00"));
/* 242:    */  }
/* 243:    */  
/* 250:    */  public static Date getLastTimeByDay(Date day)
/* 251:    */  {
/* 252:252 */    return format(DateFormatUtils.format(new Date(), "yyyy-MM-dd 23:59:59"));
/* 253:    */  }
/* 254:    */  
/* 263:    */  public static String msInterDateString(String strDate, int intStep)
/* 264:    */    throws ParseException
/* 265:    */  {
/* 266:266 */    String strFormat = "yyyy-MM-dd";
/* 267:267 */    Date dtDate = null;
/* 268:268 */    Calendar cal = Calendar.getInstance();
/* 269:269 */    SimpleDateFormat myFormatter = new SimpleDateFormat(strFormat);
/* 270:270 */    myFormatter.setLenient(false);
/* 271:271 */    dtDate = myFormatter.parse(strDate);
/* 272:    */    
/* 273:273 */    cal.setTime(dtDate);
/* 274:274 */    cal.add(5, intStep);
/* 275:    */    
/* 276:276 */    return msFormatDateTime(cal.getTime(), "yyyy-MM-dd");
/* 277:    */  }
/* 278:    */  
/* 288:    */  public static String msFormatDateTime(Date dtmDate, String strFormat)
/* 289:    */  {
/* 290:290 */    if (strFormat.equals("")) {
/* 291:291 */      strFormat = "yyyy-MM-dd HH:mm:ss";
/* 292:    */    }
/* 293:    */    
/* 294:294 */    SimpleDateFormat myFormat = new SimpleDateFormat(strFormat);
/* 295:    */    
/* 296:296 */    return myFormat.format(Long.valueOf(dtmDate.getTime()));
/* 297:    */  }
/* 298:    */  
/* 307:    */  public static String msInterMonthString(String strDate, int intStep)
/* 308:    */    throws ParseException
/* 309:    */  {
/* 310:310 */    String strFormat = "yyyy-MM-dd";
/* 311:311 */    Date dtDate = null;
/* 312:312 */    Calendar cal = Calendar.getInstance();
/* 313:313 */    SimpleDateFormat myFormatter = new SimpleDateFormat(strFormat);
/* 314:314 */    myFormatter.setLenient(false);
/* 315:315 */    dtDate = myFormatter.parse(strDate);
/* 316:    */    
/* 317:317 */    cal.setTime(dtDate);
/* 318:318 */    cal.add(2, intStep);
/* 319:    */    
/* 320:320 */    return msFormatDateTime(cal.getTime(), "yyyy-MM-dd");
/* 321:    */  }
/* 322:    */  
/* 331:    */  @Deprecated
/* 332:    */  public static int dateDiff(Date startDate, Date endDate)
/* 333:    */  {
/* 334:334 */    assert ((startDate.getTime() % 86400000L == 0L) && (endDate.getTime() % 86400000L == 0L));
/* 335:335 */    long interval = endDate.getTime() - startDate.getTime();
/* 336:336 */    return (int)(interval / 86400000L);
/* 337:    */  }
/* 338:    */  
/* 347:    */  public static int dateDayDiff(Date startDate, Date endDate)
/* 348:    */  {
/* 349:349 */    Calendar c = new GregorianCalendar();
/* 350:350 */    c.setTime(endDate);
/* 351:351 */    int endDay = c.get(6);
/* 352:352 */    c.setTime(startDate);
/* 353:353 */    int startDay = c.get(6);
/* 354:354 */    return endDay - startDay;
/* 355:    */  }
/* 356:    */  
/* 364:    */  public static Date string2Date(String strDate, String formatStr)
/* 365:    */  {
/* 366:    */    try
/* 367:    */    {
/* 368:368 */      return string2Date(strDate, formatStr, Locale.getDefault());
/* 369:    */    } catch (ParseException e) {}
/* 370:370 */    return null;
/* 371:    */  }
/* 372:    */  
/* 385:    */  private static Date string2Date(String strDate, String formatStr, Locale locale)
/* 386:    */    throws ParseException
/* 387:    */  {
/* 388:388 */    Date date = new SimpleDateFormat(formatStr, locale).parse(strDate);
/* 389:389 */    return date;
/* 390:    */  }
/* 391:    */  
/* 399:    */  public static boolean isDate(String strDate)
/* 400:    */  {
/* 401:401 */    if (StringUtils.isBlank(strDate)) {
/* 402:402 */      return false;
/* 403:    */    }
/* 404:404 */    if (strDate.length() == 8)
/* 405:405 */      return isDate(octetDate2TensDate(strDate), "-");
/* 406:406 */    if (strDate.length() == 10) {
/* 407:407 */      return isDate(strDate, "-");
/* 408:    */    }
/* 409:409 */    return false;
/* 410:    */  }
/* 411:    */  
/* 414:    */  public static String octetDate2TensDate(String strDate)
/* 415:    */  {
/* 416:416 */    if (StringUtils.isBlank(strDate)) {
/* 417:417 */      return null;
/* 418:    */    }
/* 419:419 */    StringBuilder sb = new StringBuilder(strDate);
/* 420:420 */    sb.insert(4, "-");
/* 421:421 */    sb.insert(7, "-");
/* 422:422 */    return sb.toString();
/* 423:    */  }
/* 424:    */  
/* 426:    */  public static boolean isDate(String strDate, String splitChar)
/* 427:    */  {
/* 428:428 */    if (strDate != null) {
/* 429:429 */      String[] arrDate = strDate.split(new StringBuilder().append("\\").append(splitChar).toString());
/* 430:    */      
/* 431:431 */      if (arrDate.length != 3) {
/* 432:432 */        return false;
/* 433:    */      }
/* 434:434 */      if ((arrDate[0].length() != 4) || (arrDate[1].length() > 2) || (arrDate[2].length() > 2) || (arrDate[1].length() < 1) || 
/* 435:435 */        (arrDate[2].length() < 1)) {
/* 436:436 */        return false;
/* 437:    */      }
/* 438:438 */      int year = 0;
/* 439:439 */      int month = 0;
/* 440:440 */      int day = 0;
/* 441:    */      
/* 442:442 */      year = NumberUtils.toInt(arrDate[0], -1);
/* 443:443 */      month = NumberUtils.toInt(arrDate[1], -1);
/* 444:444 */      day = NumberUtils.toInt(arrDate[2], -1);
/* 445:445 */      if ((year == -1) || (month == -1) || (day == -1)) {
/* 446:446 */        return false;
/* 447:    */      }
/* 448:448 */      if ((year < 1000) || (year > 9999)) {
/* 449:449 */        return false;
/* 450:    */      }
/* 451:451 */      if ((month < 1) || (month > 12)) {
/* 452:452 */        return false;
/* 453:    */      }
/* 454:454 */      if (day == 0) {
/* 455:455 */        return false;
/* 456:    */      }
/* 457:457 */      if (day > 31) {
/* 458:458 */        return false;
/* 459:    */      }
/* 460:    */      
/* 461:461 */      switch (month) {
/* 462:    */      case 2: 
/* 463:463 */        if (day > 29) {
/* 464:464 */          return false;
/* 465:    */        }
/* 466:    */        
/* 467:467 */        if ((year % 4 > 0) && (day > 28)) {
/* 468:468 */          return false;
/* 469:    */        }
/* 470:    */      case 4: 
/* 471:    */      case 6: 
/* 472:    */      case 9: 
/* 473:    */      case 11: 
/* 474:474 */        if (day > 30)
/* 475:475 */          return false;
/* 476:    */        break;
/* 477:    */      }
/* 478:    */    } else {
/* 479:479 */      return false;
/* 480:    */    }
/* 481:    */    
/* 482:482 */    return true;
/* 483:    */  }
/* 484:    */  
/* 492:    */  public static Date msInterDate(String strDate, int intStep)
/* 493:    */    throws ParseException
/* 494:    */  {
/* 495:495 */    String strFormat = "yyyy-MM-dd";
/* 496:496 */    Date dtDate = null;
/* 497:497 */    Calendar cal = Calendar.getInstance();
/* 498:498 */    SimpleDateFormat myFormatter = new SimpleDateFormat(strFormat);
/* 499:499 */    myFormatter.setLenient(false);
/* 500:500 */    dtDate = myFormatter.parse(strDate);
/* 501:    */    
/* 502:502 */    cal.setTime(dtDate);
/* 503:503 */    cal.add(5, intStep);
/* 504:    */    
/* 505:505 */    return cal.getTime();
/* 506:    */  }
/* 507:    */  
/* 511:    */  public static String getFirstDayByMonth(int month)
/* 512:    */  {
/* 513:513 */    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
/* 514:    */    
/* 515:515 */    Calendar cale = Calendar.getInstance();
/* 516:516 */    cale.add(2, month);
/* 517:517 */    cale.set(5, 1);
/* 518:518 */    String firstDay = format.format(cale.getTime());
/* 519:519 */    return firstDay;
/* 520:    */  }
/* 521:    */  
/* 527:    */  public static String getFirstDayByMonth(Date time, int month)
/* 528:    */  {
/* 529:529 */    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
/* 530:    */    
/* 531:531 */    Calendar cale = new GregorianCalendar();
/* 532:532 */    cale.setTime(time);
/* 533:533 */    cale.add(2, month);
/* 534:534 */    cale.set(5, 1);
/* 535:535 */    String firstDay = format.format(cale.getTime());
/* 536:536 */    return firstDay;
/* 537:    */  }
/* 538:    */  
/* 542:    */  public static String getLastDayByMonth(int month)
/* 543:    */  {
/* 544:544 */    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
/* 545:    */    
/* 546:546 */    Calendar cale = Calendar.getInstance();
/* 547:547 */    cale.add(2, month + 1);
/* 548:548 */    cale.set(5, 0);
/* 549:549 */    String lastDay = format.format(cale.getTime());
/* 550:550 */    return lastDay;
/* 551:    */  }
/* 552:    */  
/* 557:    */  public static String getLastDayByMonth(Date time, int month)
/* 558:    */  {
/* 559:559 */    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
/* 560:    */    
/* 561:561 */    Calendar cale = new GregorianCalendar();
/* 562:562 */    cale.setTime(time);
/* 563:563 */    cale.add(2, month + 1);
/* 564:564 */    cale.set(5, 0);
/* 565:565 */    String lastDay = format.format(cale.getTime());
/* 566:566 */    return lastDay;
/* 567:    */  }
/* 568:    */  
/* 578:    */  public static int msCompareDate(String strDate1, String strDate2)
/* 579:    */    throws ParseException
/* 580:    */  {
/* 581:581 */    String strFormat = "yyyy-MM-dd";
/* 582:582 */    Date dtDate1 = null;
/* 583:583 */    Date dtDate2 = null;
/* 584:584 */    int intCom = 0;
/* 585:585 */    SimpleDateFormat myFormatter = new SimpleDateFormat(strFormat);
/* 586:586 */    myFormatter.setLenient(false);
/* 587:587 */    dtDate1 = myFormatter.parse(strDate1);
/* 588:588 */    dtDate2 = myFormatter.parse(strDate2);
/* 589:    */    
/* 590:590 */    intCom = dtDate1.compareTo(dtDate2);
/* 591:591 */    if (intCom > 0) {
/* 592:592 */      return 1;
/* 593:    */    }
/* 594:594 */    if (intCom < 0) {
/* 595:595 */      return -1;
/* 596:    */    }
/* 597:597 */    return 0;
/* 598:    */  }
/* 599:    */  
/* 604:    */  public static long getUnixTimestamp(Date date)
/* 605:    */  {
/* 606:606 */    return date == null ? 0L : date.getTime() / 1000L;
/* 607:    */  }
/* 608:    */  
/* 609:    */  public static long getUnixTimestampNow() {
/* 610:610 */    Date date = new Date();
/* 611:611 */    return date.getTime() / 1000L;
/* 612:    */  }
/* 613:    */  
/* 619:    */  public static Date getEveryFewDate(Date date, int day)
/* 620:    */  {
/* 621:621 */    Calendar c = new GregorianCalendar();
/* 622:622 */    c.setTime(date);
/* 623:623 */    c.add(6, day);
/* 624:    */    
/* 625:625 */    return c.getTime();
/* 626:    */  }
/* 627:    */}


/* Location:           D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-SNAPSHOT.jar
 * Qualified Name:     com.gep.core.util.DateUtils
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */