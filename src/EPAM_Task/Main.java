package EPAM_Task;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import javax.xml.parsers.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import java.io.IOException;

public class Main {
    private static ArrayList<Employees> workers = new ArrayList<>();
    private static ArrayList<Team> team = new ArrayList<>();

    private static Node getProject_Name(Document doc, String name) {
        Element project_name = doc.createElement("Project Name");
        project_name.setAttribute("name", name);
        return project_name;
    }
    private static Node getDeadline(Document doc, String d) {
        Element dead1 = doc.createElement("Deadline");
        dead1.setAttribute("deadline", d);
        return dead1;
    }
    private static Node getBudget(Document doc, String b) {
        Element budg = doc.createElement("Budget");
        budg.setAttribute("budget", b);
        return budg;
    }
    private static Node getWorker(Document doc, String name, String l_n, String pos, String wage, String w_e) {
        Element work1 = doc.createElement("Worker");
        work1.setAttribute("name", name);
        work1.setAttribute("last name", l_n);
        work1.setAttribute("position", pos);
        work1.setAttribute("wage", wage);
        work1.setAttribute("work experience", w_e);
        return work1;
    }


    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException,
            TransformerConfigurationException, TransformerException {
        String filepath = "C:/Users/Anna/IdeaProjects/EPAM_Task/team.xml";
        File f1 = new File(filepath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(f1);
        Document doc = builder.newDocument(); //document for the final result

        NodeList workerElements = document.getDocumentElement().getElementsByTagName("worker");
        Element rootEl = document.getDocumentElement();
        NodeList teamsElements = rootEl.getElementsByTagName("teams");
        for (int i = 0; i < workerElements.getLength(); i++) {
            Node worker = workerElements.item(i);
            NamedNodeMap attributes = worker.getAttributes();
            workers.add(new Employees(attributes.getNamedItem("name").getNodeValue(),
                    attributes.getNamedItem("last_name").getNodeValue(),
                    attributes.getNamedItem("wage").getNodeValue(), attributes.getNamedItem("position").getNodeValue(),
                    attributes.getNamedItem("work_exp").getNodeValue()));
            Element item = (Element) teamsElements.item(i);
        }

        int size = workers.size();
        Team t1[] = new Team[10];
        int count = 0;
        Employees[] empArray = workers.toArray(new Employees[0]);
        for(int n = 0; n < size; n++){
            if(empArray[n].getEmPos() == "Programmer" || empArray[n].getEmPos() == "Tester" ||
                    empArray[n].getEmPos() == "Business analytic") {
                t1[count] = new Team(empArray[n].getEmName(), empArray[n].getLast_name(), empArray[n].getWage(),
                        empArray[n].getEmPos(), empArray[n].getWork());
                count++;
            }
        }
        LowExp l_exp1[] = new LowExp[5];
        HighExp h_exp1[] = new HighExp[5];
        int x = 0, y = 0;
        for(int t = 0; t < size; t++){
            if(t1[t].getWork_exp() >= 5){
                h_exp1[x] = new HighExp(empArray[t].getEmName(), empArray[t].getLast_name(), empArray[t].getWage(),
                        empArray[t].getEmPos(), empArray[t].getWork());
                x++; // x + 1 = the number of high experienced workers
            } else if (empArray[t].getWork_exp() > 0 && empArray[t].getWork_exp() < 5){
                l_exp1[y] = new LowExp(empArray[t].getEmName(), empArray[t].getLast_name(), empArray[t].getWage(),
                        empArray[t].getEmPos(), empArray[t].getWork());
                y++; // y + 1 = the number of low experienced workers
            }
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the name of the project: ");
        String b = sc.nextLine();
        System.out.println("Enter the number of specialists needed for project completion: ");
        int a = sc.nextInt();
        System.out.println("Enter the budget for the project (in $): ");
        int c = sc.nextInt();
        System.out.println("Enter the number of weeks for project completion: ");
        double d = sc.nextDouble();
        Project p1 = new Project(a, b, c, d);

        //the creation of the final document
        Element rootElement = doc.createElement
                ("Project");
        rootElement.appendChild(rootElement); //Adding the root element to the document\
        rootElement.appendChild(getProject_Name(doc, p1.getName()));
        rootElement.appendChild(getBudget(doc, p1.getBudget()));
        rootElement.appendChild(getDeadline(doc, p1.getDeadline()));
        StreamResult file = new StreamResult(new File("Users\\Anna\\IdeaProjects\\EPAM_Task\\FinalResult.xml"));
        TransformerFactory transformerfactory = TransformerFactory.newInstance();
        Transformer transformer = transformerfactory.newTransformer();
        DOMSource source = new DOMSource(doc);


        double budget_low = 0, budget_high = 0, budget = 0;
        if (a > 5) {
            if(a > 10){System.out.println("The team cannot be collected");}
            else{
                for(int yx = 0; yx <= 10; yx++) {
                    budget += t1[yx].countWage() * p1.getD();
                } if(budget <= p1.getB()){
                    for(int m = 0; m < a; m++){
                        rootElement.appendChild(getWorker(doc, t1[m].getEmName(), t1[m].getLast_name(), t1[m].getEmPos(),
                                t1[m].getWage(), t1[m].getWork()));
                    } transformer.transform(source, file);
                    System.out.println("Team collected from both low and high experienced workers");
                } else {System.out.println("The team cannot be collected");}
            }
        } else if (a > 0 && a <= 5){
            for (int xy = 0; xy < a; xy++){
                budget_low += l_exp1[xy].countWage() * p1.getD();
                budget_high += h_exp1[xy].countWage() * p1.getD();
            }
            if(budget_low > p1.getB() && budget_high > p1.getB()) {
                for(int yx = 0; yx <= 5; yx++) {
                    budget += t1[yx].countWage() * p1.getD();
                } if(budget <= p1.getB()){
                    for(int m = 0; m < a; m++){
                        rootElement.appendChild(getWorker(doc, t1[m].getEmName(), t1[m].getLast_name(), t1[m].getEmPos(),
                                t1[m].getWage(), t1[m].getWork()));
                    } transformer.transform(source, file);
                    System.out.println("Team collected from both low and high experienced workers");
                } else {System.out.println("The team cannot be collected");}
            }
            else if (budget_high > p1.getB() && budget_low < p1.getB()){
                for(int m = 0; m < a; m++){
                    rootElement.appendChild(getWorker(doc, l_exp1[m].getEmName(), l_exp1[m].getLast_name(), l_exp1[m].getEmPos(),
                            l_exp1[m].getWage(), l_exp1[m].getWork()));}
                transformer.transform(source, file);
                System.out.println("Team collected from low experienced workers according to the budget");
            } else if(budget_low < p1.getB() && budget_high < p1.getB()) {
                for(int m = 0; m < a; m++){
                rootElement.appendChild(getWorker(doc, h_exp1[m].getEmName(), h_exp1[m].getLast_name(), h_exp1[m].getEmPos(),
                        h_exp1[m].getWage(), h_exp1[m].getWork()));}
                transformer.transform(source, file);
                System.out.println("Team collected from high experienced workers according to the budget");
            }
        }
    }
}
