Feature: Bowling Game

  Scenario Outline: Scoring a game with all missed pins
    Given a game with knocked pins represented with "<frames>"
    When the score is calculated
    Then the score is <score>
    Examples:
      | frames                        | score |
      | --,--,--,--,--,--,--,--,--,-- | 0     |