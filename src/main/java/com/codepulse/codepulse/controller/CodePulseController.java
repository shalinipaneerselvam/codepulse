package com.codepulse.codepulse.controller;

import com.codepulse.codepulse.service.ProgressService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Controller
public class CodePulseController {

    // =========================================================
    // ======================= PROGRESS ========================
    // =========================================================

    // 7 Basic + 7 Array + 7 String = 21
    private static final int TOTAL_PROBLEMS = 21;

    private final ProgressService progressService;

    public CodePulseController(ProgressService progressService) {
        this.progressService = progressService;
    }


    // =========================================================
    // ======================= DASHBOARD =======================
    // =========================================================

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        String username = getCurrentUsername();

        int problemsSolved =
                progressService.getProblemsSolved(username);

        int progress =
                progressService.getProgress(username);

        int currentStreak =
                progressService.getCurrentStreak(username);

        int basicSolved =
                progressService.getBasicProblemsSolved(username);

        int arraySolved =
                progressService.getArrayProblemsSolved(username);

        int stringSolved =
                progressService.getStringProblemsSolved(username);

        if (progress > 100) {
            progress = 100;
        }

        model.addAttribute("username", username);

        model.addAttribute(
                "problemsSolved",
                problemsSolved
        );

        model.addAttribute(
                "progress",
                progress
        );

        model.addAttribute(
                "currentStreak",
                currentStreak
        );

        model.addAttribute(
                "basicSolved",
                basicSolved
        );

        model.addAttribute(
                "arraySolved",
                arraySolved
        );

        model.addAttribute(
                "stringSolved",
                stringSolved
        );

        return "dashboard";
    }


    // =========================================================
    // ======================== PRACTICE =======================
    // =========================================================

    @GetMapping("/practice")
    public String practice(
            Model model,
            HttpSession session) {

        String username = getCurrentUsername();

        Set<String> solvedProblems =
                progressService.getSolvedProblems(username);

        int problemsSolved =
                progressService.getProblemsSolved(username);

        int progress =
                progressService.getProgress(username);

        int currentStreak =
                progressService.getCurrentStreak(username);

        // IMPORTANT:
        // These two values are required by practice.html
        int arraySolved =
                progressService.getArrayProblemsSolved(username);

        int stringSolved =
                progressService.getStringProblemsSolved(username);

        if (progress > 100) {
            progress = 100;
        }

        model.addAttribute(
                "problemsSolved",
                problemsSolved
        );

        model.addAttribute(
                "progress",
                progress
        );

        model.addAttribute(
                "currentStreak",
                currentStreak
        );

        model.addAttribute(
                "solvedProblems",
                solvedProblems
        );

        model.addAttribute(
                "arraySolved",
                arraySolved
        );

        model.addAttribute(
                "stringSolved",
                stringSolved
        );

        return "practice";
    }


    // =========================================================
    // ===================== BASIC PROBLEMS ====================
    // =========================================================


    // =========================================================
    // 1. LARGEST NUMBER
    // =========================================================

    @GetMapping("/practice/largest")
    public String largestProblem() {
        return "largest";
    }

    @PostMapping("/practice/largest")
    public String findLargest(
            @RequestParam("numbers") String numbers,
            Model model,
            HttpSession session) {

        try {

            String input = numbers.trim();

            if (input.isEmpty()) {

                model.addAttribute(
                        "result",
                        "Please enter numbers"
                );

                return "largest";
            }

            String[] values =
                    input.split("\\s+");

            int largest =
                    Integer.parseInt(values[0]);

            for (int i = 1;
                 i < values.length;
                 i++) {

                int number =
                        Integer.parseInt(values[i]);

                if (number > largest) {
                    largest = number;
                }
            }

            model.addAttribute(
                    "result",
                    "Largest = " + largest
            );

            markSolved(
                    session,
                    "largest"
            );

        } catch (Exception e) {

            model.addAttribute(
                    "result",
                    "Invalid input"
            );

            return "largest";
        }

        return "redirect:/practice";
    }


    // =========================================================
    // 2. REVERSE NUMBER
    // =========================================================

    @GetMapping("/practice/reverse")
    public String reverseProblem() {
        return "reverse";
    }

    @PostMapping("/practice/reverse")
    public String reverseNumber(
            @RequestParam("number") String number,
            Model model,
            HttpSession session) {

        try {

            int num =
                    Integer.parseInt(
                            number.trim()
                    );

            int original = num;
            int reversed = 0;

            while (num != 0) {

                int digit =
                        num % 10;

                reversed =
                        reversed * 10 + digit;

                num =
                        num / 10;
            }

            model.addAttribute(
                    "result",
                    "Reverse of " +
                            original +
                            " = " +
                            reversed
            );

            markSolved(
                    session,
                    "reverse"
            );

        } catch (Exception e) {

            model.addAttribute(
                    "result",
                    "Invalid input"
            );

            return "reverse";
        }

        return "redirect:/practice";
    }


    // =========================================================
    // 3. PRIME NUMBER
    // =========================================================

    @GetMapping("/practice/prime")
    public String primeProblem() {
        return "prime";
    }

    @PostMapping("/practice/prime")
    public String checkPrime(
            @RequestParam("number") String number,
            Model model,
            HttpSession session) {

        try {

            int num =
                    Integer.parseInt(
                            number.trim()
                    );

            boolean prime = true;

            if (num <= 1) {
                prime = false;
            }

            for (int i = 2;
                 i * i <= num;
                 i++) {

                if (num % i == 0) {

                    prime = false;
                    break;
                }
            }

            model.addAttribute(
                    "result",
                    prime
                            ? num + " is a Prime Number"
                            : num + " is Not a Prime Number"
            );

            markSolved(
                    session,
                    "prime"
            );

        } catch (Exception e) {

            model.addAttribute(
                    "result",
                    "Invalid input"
            );

            return "prime";
        }

        return "redirect:/practice";
    }


    // =========================================================
    // 4. SECOND LARGEST
    // =========================================================

    @GetMapping("/practice/second-largest")
    public String secondLargestProblem() {
        return "second-largest";
    }

    @PostMapping("/practice/second-largest")
    public String findSecondLargest(
            @RequestParam("numbers") String numbers,
            Model model,
            HttpSession session) {

        try {

            String input =
                    numbers.trim();

            if (input.isEmpty()) {

                model.addAttribute(
                        "result",
                        "Please enter numbers"
                );

                return "second-largest";
            }

            String[] values =
                    input.split("\\s+");

            int largest =
                    Integer.MIN_VALUE;

            int secondLargest =
                    Integer.MIN_VALUE;

            for (String value : values) {

                int number =
                        Integer.parseInt(value);

                if (number > largest) {

                    secondLargest =
                            largest;

                    largest =
                            number;

                } else if (
                        number > secondLargest
                                && number < largest
                ) {

                    secondLargest =
                            number;
                }
            }

            if (secondLargest ==
                    Integer.MIN_VALUE) {

                model.addAttribute(
                        "result",
                        "No second largest number found"
                );

                return "second-largest";
            }

            model.addAttribute(
                    "result",
                    "Second Largest = " +
                            secondLargest
            );

            markSolved(
                    session,
                    "second-largest"
            );

        } catch (NumberFormatException e) {

            model.addAttribute(
                    "result",
                    "Invalid input. Please enter numbers only"
            );

            return "second-largest";
        }

        return "redirect:/practice";
    }


    // =========================================================
    // 5. PALINDROME
    // =========================================================

    @GetMapping("/practice/palindrome")
    public String palindromeProblem() {
        return "palindrome";
    }

    @PostMapping("/practice/palindrome")
    public String checkPalindrome(
            @RequestParam("number") String number,
            Model model,
            HttpSession session) {

        try {

            int original =
                    Integer.parseInt(
                            number.trim()
                    );

            int num = original;
            int reversed = 0;

            while (num != 0) {

                int digit =
                        num % 10;

                reversed =
                        reversed * 10 + digit;

                num =
                        num / 10;
            }

            boolean palindrome =
                    original == reversed;

            model.addAttribute(
                    "result",
                    palindrome
                            ? original + " is a Palindrome"
                            : original + " is Not a Palindrome"
            );

            markSolved(
                    session,
                    "palindrome"
            );

        } catch (NumberFormatException e) {

            model.addAttribute(
                    "result",
                    "Invalid input. Please enter a number"
            );

            return "palindrome";
        }

        return "redirect:/practice";
    }


    // =========================================================
    // 6. FACTORIAL
    // =========================================================

    @GetMapping("/practice/factorial")
    public String factorialProblem() {
        return "factorial";
    }

    @PostMapping("/practice/factorial")
    public String findFactorial(
            @RequestParam("number") String number,
            Model model,
            HttpSession session) {

        try {

            int num =
                    Integer.parseInt(
                            number.trim()
                    );

            if (num < 0) {

                model.addAttribute(
                        "result",
                        "Factorial is not defined for negative numbers"
                );

                return "factorial";
            }

            long factorial = 1;

            for (int i = 1;
                 i <= num;
                 i++) {

                factorial =
                        factorial * i;
            }

            model.addAttribute(
                    "result",
                    "Factorial = " +
                            factorial
            );

            markSolved(
                    session,
                    "factorial"
            );

        } catch (NumberFormatException e) {

            model.addAttribute(
                    "result",
                    "Invalid input. Please enter a number"
            );

            return "factorial";
        }

        return "redirect:/practice";
    }


    // =========================================================
    // 7. FIBONACCI
    // =========================================================

    @GetMapping("/practice/fibonacci")
    public String fibonacciProblem() {
        return "fibonacci";
    }

    @PostMapping("/practice/fibonacci")
    public String findFibonacci(
            @RequestParam("number") String number,
            Model model,
            HttpSession session) {

        try {

            int n =
                    Integer.parseInt(
                            number.trim()
                    );

            if (n <= 0) {

                model.addAttribute(
                        "result",
                        "Please enter a positive number"
                );

                return "fibonacci";
            }

            int first = 0;
            int second = 1;

            StringBuilder series =
                    new StringBuilder();

            for (int i = 1;
                 i <= n;
                 i++) {

                series.append(first);

                if (i < n) {
                    series.append(" ");
                }

                int next =
                        first + second;

                first = second;
                second = next;
            }

            model.addAttribute(
                    "result",
                    series.toString()
            );

            markSolved(
                    session,
                    "fibonacci"
            );

        } catch (NumberFormatException e) {

            model.addAttribute(
                    "result",
                    "Invalid input. Please enter a number"
            );

            return "fibonacci";
        }

        return "redirect:/practice";
    }


    // =========================================================
    // ======================= ARRAY HOME =====================
    // =========================================================

    @GetMapping("/practice/arrays")
    public String arrays(
            Model model,
            HttpSession session) {

        String username =
                getCurrentUsername();

        Set<String> solvedProblems =
                progressService.getSolvedProblems(
                        username
                );

        int arraySolved =
                progressService.getArrayProblemsSolved(
                        username
                );

        model.addAttribute(
                "solvedProblems",
                solvedProblems
        );

        model.addAttribute(
                "arraySolved",
                arraySolved
        );

        return "arrays";
    }


    // =========================================================
    // ================= ARRAY TEMPLATE =======================
    // =========================================================

    private String getArrayTemplate(
            String problem) {

        switch (problem) {

            case "min-max":
                return "array-min-max";

            case "reverse":
                return "array-reverse";

            case "second-largest":
                return "array-second-largest";

            case "search":
                return "array-search";

            case "even-odd":
                return "array-even-odd";

            case "sum":
                return "array-sum";

            case "average":
                return "array-average";

            default:
                return "redirect:/practice/arrays";
        }
    }


    // =========================================================
    // ================= ARRAY PAGE ===========================
    // =========================================================

    @GetMapping("/practice/array/{problem}")
    public String arrayProblem(
            @PathVariable("problem") String problem) {

        return getArrayTemplate(problem);
    }


    // =========================================================
    // ================= ARRAY SOLUTIONS ======================
    // =========================================================

    @PostMapping("/practice/array/{problem}")
    public String solveArrayProblem(
            @PathVariable("problem") String problem,
            @RequestParam("numbers") String numbers,
            @RequestParam(
                    value = "target",
                    required = false
            ) String target,
            Model model,
            HttpSession session) {

        try {

            if (!isValidArrayProblem(problem)) {

                return "redirect:/practice/arrays";
            }

            String input =
                    numbers.trim();

            if (input.isEmpty()) {

                return showArrayError(
                        problem,
                        model,
                        "Please enter numbers"
                );
            }

            String[] values =
                    input.split("\\s+");

            int[] arr =
                    new int[values.length];

            for (int i = 0;
                 i < values.length;
                 i++) {

                arr[i] =
                        Integer.parseInt(
                                values[i]
                        );
            }


            // =================================================
            // MIN MAX
            // =================================================

            if (problem.equals("min-max")) {

                int min = arr[0];
                int max = arr[0];

                for (int num : arr) {

                    if (num < min) {
                        min = num;
                    }

                    if (num > max) {
                        max = num;
                    }
                }

                model.addAttribute(
                        "result",
                        "Minimum = " +
                                min +
                                ", Maximum = " +
                                max
                );
            }


            // =================================================
            // REVERSE
            // =================================================

            else if (problem.equals("reverse")) {

                StringBuilder result =
                        new StringBuilder();

                for (int i = arr.length - 1;
                     i >= 0;
                     i--) {

                    result.append(arr[i]);

                    if (i != 0) {
                        result.append(" ");
                    }
                }

                model.addAttribute(
                        "result",
                        result.toString()
                );
            }


            // =================================================
            // SECOND LARGEST
            // =================================================

            else if (
                    problem.equals("second-largest")
            ) {

                if (arr.length < 2) {

                    return showArrayError(
                            problem,
                            model,
                            "At least 2 numbers are required"
                    );
                }

                int largest =
                        Integer.MIN_VALUE;

                int secondLargest =
                        Integer.MIN_VALUE;

                for (int num : arr) {

                    if (num > largest) {

                        secondLargest =
                                largest;

                        largest =
                                num;

                    } else if (
                            num > secondLargest
                                    && num < largest
                    ) {

                        secondLargest =
                                num;
                    }
                }

                if (secondLargest ==
                        Integer.MIN_VALUE) {

                    return showArrayError(
                            problem,
                            model,
                            "Second largest number not found"
                    );
                }

                model.addAttribute(
                        "result",
                        "Second Largest = " +
                                secondLargest
                );
            }


            // =================================================
            // SEARCH
            // =================================================

            else if (problem.equals("search")) {

                if (target == null ||
                        target.trim().isEmpty()) {

                    return showArrayError(
                            problem,
                            model,
                            "Please enter element to search"
                    );
                }

                int targetNumber =
                        Integer.parseInt(
                                target.trim()
                        );

                boolean found = false;

                for (int num : arr) {

                    if (num == targetNumber) {

                        found = true;
                        break;
                    }
                }

                model.addAttribute(
                        "result",
                        found
                                ? targetNumber +
                                " is present in the array"
                                : targetNumber +
                                " is not present in the array"
                );
            }


            // =================================================
            // EVEN ODD
            // =================================================

            else if (problem.equals("even-odd")) {

                int even = 0;
                int odd = 0;

                for (int num : arr) {

                    if (num % 2 == 0) {
                        even++;
                    } else {
                        odd++;
                    }
                }

                model.addAttribute(
                        "result",
                        "Even = " +
                                even +
                                ", Odd = " +
                                odd
                );
            }


            // =================================================
            // SUM
            // =================================================

            else if (problem.equals("sum")) {

                int sum = 0;

                for (int num : arr) {

                    sum += num;
                }

                model.addAttribute(
                        "result",
                        "Sum = " + sum
                );
            }


            // =================================================
            // AVERAGE
            // =================================================

            else if (problem.equals("average")) {

                int sum = 0;

                for (int num : arr) {

                    sum += num;
                }

                double average =
                        (double) sum / arr.length;

                model.addAttribute(
                        "result",
                        "Average = " + average
                );
            }


            // =================================================
            // MARK ARRAY PROBLEM AS SOLVED
            // =================================================

            markSolved(
                    session,
                    "array-" + problem
            );

            return getArrayTemplate(problem);

        } catch (NumberFormatException e) {

            return showArrayError(
                    problem,
                    model,
                    "Invalid input. Please enter numbers only"
            );
        }
    }


    // =========================================================
    // ================= VALID ARRAY ==========================
    // =========================================================

    private boolean isValidArrayProblem(
            String problem) {

        return problem.equals("min-max")
                || problem.equals("reverse")
                || problem.equals("second-largest")
                || problem.equals("search")
                || problem.equals("even-odd")
                || problem.equals("sum")
                || problem.equals("average");
    }


    // =========================================================
    // ================= ARRAY ERROR ==========================
    // =========================================================

    private String showArrayError(
            String problem,
            Model model,
            String message) {

        model.addAttribute(
                "result",
                message
        );

        return getArrayTemplate(problem);
    }


    // =========================================================
    // ===================== STRING HOME ======================
    // =========================================================

    @GetMapping("/practice/strings")
    public String strings(
            Model model,
            HttpSession session) {

        String username =
                getCurrentUsername();

        Set<String> solvedProblems =
                progressService.getSolvedProblems(
                        username
                );

        int stringSolved =
                progressService.getStringProblemsSolved(
                        username
                );

        model.addAttribute(
                "solvedProblems",
                solvedProblems
        );

        model.addAttribute(
                "stringSolved",
                stringSolved
        );

        return "strings";
    }


    // =========================================================
    // ================= STRING TEMPLATE ======================
    // =========================================================

    private String getStringTemplate(
            String problem) {

        switch (problem) {

            case "reverse":
                return "string-reverse";

            case "palindrome":
                return "string-palindrome";

            case "vowels-consonants":
                return "string-vowels-consonants";

            case "count-characters":
                return "string-count-characters";

            case "remove-spaces":
                return "string-remove-spaces";

            case "anagram":
                return "string-anagram";

            case "duplicates":
                return "string-duplicates";

            default:
                return "redirect:/practice/strings";
        }
    }


    // =========================================================
    // ================= STRING PAGE ==========================
    // =========================================================

    @GetMapping("/practice/string/{problem}")
    public String stringProblem(
            @PathVariable("problem") String problem) {

        return getStringTemplate(problem);
    }


    // =========================================================
    // ================= STRING SOLUTIONS =====================
    // =========================================================

    @PostMapping("/practice/string/{problem}")
    public String solveStringProblem(
            @PathVariable("problem") String problem,
            @RequestParam("text") String text,
            @RequestParam(
                    value = "secondText",
                    required = false
            ) String secondText,
            Model model,
            HttpSession session) {

        try {

            if (!isValidStringProblem(problem)) {

                return "redirect:/practice/strings";
            }

            if (text == null ||
                    text.trim().isEmpty()) {

                return showStringError(
                        problem,
                        model,
                        "Please enter text"
                );
            }


            // =================================================
            // 1. REVERSE STRING
            // =================================================

            if (problem.equals("reverse")) {

                String reversed =
                        new StringBuilder(text)
                                .reverse()
                                .toString();

                model.addAttribute(
                        "result",
                        "Reverse = " + reversed
                );
            }


            // =================================================
            // 2. PALINDROME STRING
            // =================================================

            else if (problem.equals("palindrome")) {

                String cleanText =
                        text.replaceAll(
                                "\\s+",
                                ""
                        ).toLowerCase();

                String reversed =
                        new StringBuilder(cleanText)
                                .reverse()
                                .toString();

                boolean palindrome =
                        cleanText.equals(reversed);

                model.addAttribute(
                        "result",
                        palindrome
                                ? "Palindrome"
                                : "Not a Palindrome"
                );
            }


            // =================================================
            // 3. VOWELS & CONSONANTS
            // =================================================

            else if (
                    problem.equals(
                            "vowels-consonants"
                    )
            ) {

                int vowels = 0;
                int consonants = 0;

                for (int i = 0;
                     i < text.length();
                     i++) {

                    char ch =
                            Character.toLowerCase(
                                    text.charAt(i)
                            );

                    if (ch >= 'a' &&
                            ch <= 'z') {

                        if (ch == 'a' ||
                                ch == 'e' ||
                                ch == 'i' ||
                                ch == 'o' ||
                                ch == 'u') {

                            vowels++;

                        } else {

                            consonants++;
                        }
                    }
                }

                model.addAttribute(
                        "result",
                        "Vowels = " +
                                vowels +
                                ", Consonants = " +
                                consonants
                );
            }


            // =================================================
            // 4. COUNT CHARACTERS
            // =================================================

            else if (
                    problem.equals(
                            "count-characters"
                    )
            ) {

                int count = 0;

                for (int i = 0;
                     i < text.length();
                     i++) {

                    if (!Character.isWhitespace(
                            text.charAt(i)
                    )) {

                        count++;
                    }
                }

                model.addAttribute(
                        "result",
                        "Characters = " +
                                count
                );
            }


            // =================================================
            // 5. REMOVE SPACES
            // =================================================

            else if (
                    problem.equals(
                            "remove-spaces"
                    )
            ) {

                String result =
                        text.replaceAll(
                                "\\s+",
                                ""
                        );

                model.addAttribute(
                        "result",
                        "Without Spaces = " +
                                result
                );
            }


            // =================================================
            // 6. ANAGRAM
            // =================================================

            else if (
                    problem.equals("anagram")
            ) {

                if (secondText == null ||
                        secondText.trim().isEmpty()) {

                    return showStringError(
                            problem,
                            model,
                            "Please enter second string"
                    );
                }

                String first =
                        text.replaceAll(
                                "\\s+",
                                ""
                        ).toLowerCase();

                String second =
                        secondText.replaceAll(
                                "\\s+",
                                ""
                        ).toLowerCase();

                if (first.length() !=
                        second.length()) {

                    model.addAttribute(
                            "result",
                            "Not Anagram"
                    );

                } else {

                    char[] firstArray =
                            first.toCharArray();

                    char[] secondArray =
                            second.toCharArray();

                    Arrays.sort(
                            firstArray
                    );

                    Arrays.sort(
                            secondArray
                    );

                    boolean anagram =
                            Arrays.equals(
                                    firstArray,
                                    secondArray
                            );

                    model.addAttribute(
                            "result",
                            anagram
                                    ? "Anagram"
                                    : "Not Anagram"
                    );
                }
            }


            // =================================================
            // 7. DUPLICATE CHARACTERS
            // =================================================

            else if (
                    problem.equals(
                            "duplicates"
                    )
            ) {

                Set<Character> seen =
                        new HashSet<>();

                Set<Character> duplicates =
                        new LinkedHashSet<>();

                for (int i = 0;
                     i < text.length();
                     i++) {

                    char ch =
                            text.charAt(i);

                    if (ch == ' ') {
                        continue;
                    }

                    if (!seen.add(ch)) {

                        duplicates.add(ch);
                    }
                }

                model.addAttribute(
                        "result",
                        duplicates.isEmpty()
                                ? "No duplicate characters"
                                : "Duplicate Characters = " +
                                duplicates
                );
            }


            // =================================================
            // MARK STRING PROBLEM AS SOLVED
            // =================================================

            markSolved(
                    session,
                    "string-" + problem
            );

            return getStringTemplate(problem);

        } catch (Exception e) {

            return showStringError(
                    problem,
                    model,
                    "Invalid input"
            );
        }
    }


    // =========================================================
    // ================= VALID STRING =========================
    // =========================================================

    private boolean isValidStringProblem(
            String problem) {

        return problem.equals("reverse")
                || problem.equals("palindrome")
                || problem.equals("vowels-consonants")
                || problem.equals("count-characters")
                || problem.equals("remove-spaces")
                || problem.equals("anagram")
                || problem.equals("duplicates");
    }


    // =========================================================
    // ================= STRING ERROR =========================
    // =========================================================

    private String showStringError(
            String problem,
            Model model,
            String message) {

        model.addAttribute(
                "result",
                message
        );

        return getStringTemplate(problem);
    }


    // =========================================================
    // ================= CURRENT USER =========================
    // =========================================================

    private String getCurrentUsername() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(
                authentication.getName()
        )) {

            throw new IllegalStateException(
                    "User is not authenticated"
            );
        }

        return authentication.getName();
    }


    // =========================================================
    // ================= GET SOLVED PROBLEMS ==================
    // =========================================================

    private Set<String> getSolvedProblems(
            HttpSession session) {

        return progressService.getSolvedProblems(
                getCurrentUsername()
        );
    }


    // =========================================================
    // ==================== MARK SOLVED ========================
    // =========================================================

    private void markSolved(
            HttpSession session,
            String problemName) {

        progressService.markSolved(
                getCurrentUsername(),
                problemName
        );
    }
}