using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Data.OleDb;

namespace EIT_B5
{
    public partial class Po_Antikvitetu : Form
    {
        public Po_Antikvitetu()
        {
            InitializeComponent();
        }
        OleDbConnection konekcija;
        OleDbCommand komanda;
        DataTable dt;
        OleDbDataAdapter da;
        string duzina, sirina;
        void Konekcija()
        {
            konekcija = new OleDbConnection();
            konekcija.ConnectionString = @"Provider=Microsoft.ACE.OLEDB.12.0;Data Source=|DataDirectory|\Antikviteti i lokacije - osnovno.accdb";
            konekcija.Open();
            komanda = new OleDbCommand();
            komanda.Connection = konekcija;
        }
        
        private void button1_Click(object sender, EventArgs e)
        {
            
             if (textBox1.Text != string.Empty)
            {
                button2.Enabled = true;
            Konekcija();
            
            string prvo = "SELECT Lokalitet.LokalitetID,Lokalitet.KoordinateDuzina,Lokalitet.KoordinateSirina ";
            string drugo = "FROM ((Antikvitet INNER JOIN Lokalitet ON Antikvitet.LokalitetID=Lokalitet.LokalitetID)INNER JOIN TIP_ANTIKVITETA ON Antikvitet.TipAntikviteta=TIP_ANTIKVITETA.TipAntikvitetaID) WHERE(TIP_ANTIKVITETA.Tip='{0}')";
            
            komanda.CommandText = string.Format(prvo + drugo,textBox1.Text);

            dt = new DataTable();
            da = new OleDbDataAdapter();
            da.SelectCommand = komanda;
            da.Fill(dt);
            dataGridView1.DataSource = dt;
            
            
            konekcija.Close();
            }
             else
                 MessageBox.Show("Morate popuniti");
        }

        private void button3_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void button2_Click(object sender, EventArgs e)
        {
          
            duzina = Convert.ToString( dt.Rows[0]["KoordinateDuzina"]);
            sirina=Convert.ToString(dt.Rows[0]["KoordinateSirina"]);
            string[] du = duzina.Split(' ');
            string[] si = sirina.Split(' ');
            double x = 0;
            double y = 0;
            if (du[1] == "IGD")
            {
                x = Convert.ToDouble(du[0]);
            }
            else
            {
                x = -Convert.ToDouble(du[0]);
            }

            if (si[1] == "SGS")
            {
                y = -Convert.ToDouble(si[0]);
            }
            else
            {
                y = Convert.ToDouble(si[0]);
            }
            Pen olovka = new Pen(Color.Red, 3);
            Graphics g = pictureBox1.CreateGraphics();
            g.Clear(Color.White);
            g.DrawRectangle(olovka, 0, 0, pictureBox1.Width-1, pictureBox1.Height-1);
            g.DrawLine(olovka, pictureBox1.Width / 2, 0, pictureBox1.Width / 2, pictureBox1.Height);
            g.DrawLine(olovka,0 , pictureBox1.Height / 2, pictureBox1.Width , pictureBox1.Height/2);
            g.DrawEllipse(olovka,pictureBox1.Width/2+((int)x - 5),pictureBox1.Height/2+((int)y -5) , 5, 5);
            
        }

        private void Po_Antikvitetu_Load(object sender, EventArgs e)
        {
        

                
        }
    }
}
